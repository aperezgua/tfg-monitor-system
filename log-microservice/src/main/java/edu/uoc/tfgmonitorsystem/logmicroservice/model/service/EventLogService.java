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
import java.util.Map.Entry;
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
        List<EventLog> fullFilledEvents = new ArrayList<>();

        Map<Rule, EventLog> currentEvent = new HashMap<>();

        if (agent.isPresent()) {
            clearEventsFromAgent(agentTokenId);

            List<Log> logs = logService.findByAgent(new AgentLogFilter(agentTokenId));

            for (Log log : logs) {
                for (Rule rule : agent.get().getRules()) {
                    EventLog eventLog;
                    if (!currentEvent.containsKey(rule)) {
                        eventLog = new EventLog(agent.get(), rule);
                        currentEvent.put(rule, eventLog);
                    } else {
                        eventLog = currentEvent.get(rule);
                    }
                    processLog(eventLog, log);

                    if (eventLog.isFullFilled()) {
                        fullFilledEvents.add(eventLog);
                        currentEvent.remove(rule);
                    }
                }
            }

            for (EventLog event : fullFilledEvents) {
                event.setId((int) dbSequenceService.generateDbSequence(EventLog.SEQUENCE_NAME));
                eventLogRepository.save(event);
            }

            for (Entry<Rule, EventLog> entry : currentEvent.entrySet()) {
                entry.getValue().setId((int) dbSequenceService.generateDbSequence(EventLog.SEQUENCE_NAME));
                eventLogRepository.save(entry.getValue());
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

    /**
     * Verifica si se cumple una condición de conteo para un evento y un log en concreto.
     *
     * @param eventLog  Evento que está siendo procesado.
     * @param log       Log que se quiere procesar.
     * @param condition Condición de la regla del evento
     * @param value     Valor de la condición acumulado.
     */
    private void checkCountValue(EventLog eventLog, Log log, Condition condition, ConditionValue value) {
        if (condition.needDoubleValueComparation()) {
            value.updateCountValue(log.getDate(), condition.getTime());
            if (condition.matchValue(value.getTotalValue())) {
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
     * @param eventLog  Evento que está siendo procesado.
     * @param log       Log que se quiere procesar.
     * @param condition Condición de la regla del evento
     * @param value     Valor de la condición acumulado.
     */
    private void checkDirectValue(EventLog eventLog, Log log, Condition condition, ConditionValue value) {

        if (condition.needAccumulatedAvgValue()) {
            Double doubleValue = RegexpUtil.getDoubleFromString(log.getLogLine());
            if (doubleValue != null) {
                value.updateValue(log.getDate(), condition.getTime(), doubleValue);
                if (condition.matchValue(value.getAvgValue())) {
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

}
