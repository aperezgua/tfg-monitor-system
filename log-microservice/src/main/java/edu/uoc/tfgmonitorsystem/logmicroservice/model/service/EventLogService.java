package edu.uoc.tfgmonitorsystem.logmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.Condition;
import edu.uoc.tfgmonitorsystem.common.model.document.ConditionValue;
import edu.uoc.tfgmonitorsystem.common.model.document.EventLog;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.document.Rule;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.AgentRepository;
import edu.uoc.tfgmonitorsystem.common.model.repository.EventLogRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import edu.uoc.tfgmonitorsystem.common.model.util.RegexpUtil;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.AgentLogFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EventLogService implements IEventLogService {
    /**
     * Para secuencias de autonuméricos.
     */
    @Autowired
    private IDbSequenceService dbSequenceService;
    /**
     * Template para queries.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     */
    @Autowired
    private EventLogRepository eventLogRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ILogService logService;

    @Override
    public void processExistentLog(String agentTokenId) throws TfgMonitorSystenException {

        Optional<Agent> agent = agentRepository.findById(agentTokenId);
        List<EventLog> events = new ArrayList<>();

        Map<Rule, EventLog> currentEvent = new HashMap<>();

        if (agent.isPresent()) {
            clearEventsFromAgent(agentTokenId);

            List<Log> logs = logService.findByAgent(new AgentLogFilter(agentTokenId));

            for (Log log : logs) {
                for (Rule rule : agent.get().getRules()) {
                    EventLog eventLog;
                    if (!currentEvent.containsKey(rule)) {
                        eventLog = new EventLog(agent.get(), rule);
                        events.add(eventLog);
                        currentEvent.put(rule, eventLog);
                    } else {
                        eventLog = currentEvent.get(rule);
                    }

                }
            }

            // logService.findByRegexp(null)

            // List<EventLog eventLog =
        }
    }

    /**
     * Procesa una línea de log según su fecha contra este evento calculando si satisface o no las diferentes
     * condiciones de la regla.
     *
     * @param log Log con el log que debe ser procesado.
     * @return true si
     */
    public boolean processLog(EventLog eventLog, Log log) {
        if (eventLog.getInitDate() == null) {
            eventLog.setInitDate(log.getDate());
        }

        for (int i = 0; i < eventLog.conditionsValuesSize(); i++) {
            Condition condition = eventLog.findConditionByIndex(i);
            ConditionValue value = eventLog.findConditionValueByIndex(i);
            if (!value.isFullFilled()) {
                if (eventLog.isDirectValue()) {
                    checkDirectValue(eventLog, log, condition, value);
                } else {
                    checkCountValue(eventLog, log, condition, value);
                }
            }
        }

        return false;
    }

    private void checkCountValue(EventLog eventLog, Log log, Condition condition, ConditionValue value) {
        if (condition.needDoubleValueComparation()) {
            if (value.getAccumulatedValue() == null || shouldByCalculateValue(eventLog, condition, log)) {
                value.addCountValue();
            } else if (condition.matchValue(value.getAccumulatedValue())) {
                value.setFullFilled(Boolean.TRUE);
            } else {
                value.setFullFilled(Boolean.FALSE);
            }
        }
    }

    /**
     * Comprueba una línea de log contra una condición si esta no se encuentra completa usando el sistema de valor
     * directo. Primeramente se verifica si es un tipo de valor medio, en ese caso se apoya en ConditionValue para hayar
     * la media si fuese necesario. Si los valores medios/directos calculados son correctos, la condición se da por
     * correcta.
     *
     * @param eventLog
     * @param log
     * @param condition
     * @param value
     */
    private void checkDirectValue(EventLog eventLog, Log log, Condition condition, ConditionValue value) {

        if (condition.needAccumulatedAvgValue()) {
            Double doubleValue = RegexpUtil.getDoubleFromString(log.getLogLine());
            if (doubleValue != null) {
                if (value.getAccumulatedValue() == null || shouldByCalculateValue(eventLog, condition, log)) {
                    value.addAvgValue(doubleValue);
                } else if (condition.matchValue(value.getAccumulatedValue())) {
                    value.setFullFilled(Boolean.TRUE);
                } else {
                    value.setFullFilled(Boolean.FALSE);
                }
            } else {
                value.setFullFilled(Boolean.FALSE);
            }
        } else if (condition.needDoubleValueComparation()) {
            Double doubleValue = RegexpUtil.getDoubleFromString(log.getLogLine());
            if (doubleValue != null) {
                value.setFullFilled(condition.matchValue(doubleValue));
            } else {
                value.setFullFilled(Boolean.FALSE);
            }
        } else {
            value.setFullFilled(condition.matchValue(log.getLogLine()));
        }
    }

    /**
     * Elimina los eventos creados del agente
     *
     * @param agentTokenId
     * @throws TfgMonitorSystenException
     */
    private void clearEventsFromAgent(String agentTokenId) throws TfgMonitorSystenException {
        Query query = new Query(Criteria.where("agent.token").is(agentTokenId));
        List<EventLog> events = mongoTemplate.find(query, EventLog.class);

        if (!CollectionUtils.isEmpty(events)) {
            eventLogRepository.deleteAll(events);
        }
    }

    /**
     * Calcula si es necesario calcular para la línea de log informada calcular el valor acumulado,
     *
     * @param eventLog
     * @param condition
     * @param log
     * @return
     */
    private boolean shouldByCalculateValue(EventLog eventLog, Condition condition, Log log) {
        return condition.getTime() != null
                && log.getDate().getTime() - eventLog.getInitDate().getTime() < condition.getTime() * 1000;
    }

}
