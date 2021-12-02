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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EventLogService implements IEventLogService {

    private static final Logger LOGGER = Logger.getLogger(EventLogService.class);
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

        Map<String, EventLog> currentEvent = new HashMap<>();

        if (agent.isPresent()) {
            clearEventsFromAgent(agentTokenId);

            List<Log> logs = logService.findByAgent(new AgentLogFilter(agentTokenId));
            List<EventLog> fullFilledEvents = new ArrayList<>();

            for (Log log : logs) {
                fullFilledEvents.addAll(processAgentLog(agent.get(), currentEvent, log));
            }

            saveEvents(currentEvent.values(), fullFilledEvents);

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

    @Override
    public void processNewLog(String agentTokenId, Log log) throws TfgMonitorSystenException {

        Optional<Agent> agent = agentRepository.findById(agentTokenId);

        if (agent.isPresent()) {
            List<EventLog> notFullFilledEvents = findNotFullFilledEvents(agentTokenId);
            Map<String, EventLog> currentEvent = new HashMap<>();
            for (EventLog eventLog : notFullFilledEvents) {
                currentEvent.put(eventLog.getRuleName(), eventLog);
            }

            List<EventLog> fullFilledEvents = processAgentLog(agent.get(), currentEvent, log);

            saveEvents(currentEvent.values(), fullFilledEvents);
        }

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

    /**
     * Busca los eventos no completados para un agente
     *
     * @param agentTokenId
     * @return
     * @throws TfgMonitorSystenException
     */
    private List<EventLog> findNotFullFilledEvents(String agentTokenId) throws TfgMonitorSystenException {
        Query query = new Query(Criteria.where("agent.token").is(agentTokenId));
        query.addCriteria(Criteria.where("fullFilled").is(false));
        return mongoTemplate.find(query, EventLog.class);
    }

    /**
     * Dado un agent un mapa que va a contener los eventos actuales y una línea de log, la procesa generando un conjunto
     * de posibles eventos si fuese el caso.
     *
     * @param agent        Agent con el agente.
     * @param currentEvent Map con los eventos actuales
     * @param log          Linea de log
     * @return List con el conjunto de eventos completos generados
     */
    private List<EventLog> processAgentLog(Agent agent, Map<String, EventLog> currentEvent, Log log) {
        List<EventLog> fullFilledEvents = new ArrayList<>();
        if (!CollectionUtils.isEmpty(agent.getRules())) {
            for (Rule rule : agent.getRules()) {
                EventLog eventLog;
                if (!currentEvent.containsKey(rule.getName())) {
                    eventLog = new EventLog(agent, rule.getName());
                    eventLog.setId((int) dbSequenceService.generateDbSequence(EventLog.SEQUENCE_NAME));
                    currentEvent.put(rule.getName(), eventLog);
                } else {
                    eventLog = currentEvent.get(rule.getName());
                }

                processLog(eventLog, log);

                if (eventLog.computeFullFilled()) {
                    fullFilledEvents.add(eventLog);
                    currentEvent.remove(rule.getName());
                }
            }
        }
        return fullFilledEvents;
    }

    /**
     * Guarda los eventos generados por el sistema, los current que son los no completados y los completados.
     *
     * @param currentEvents
     * @param fullFilledEvents
     */
    private void saveEvents(Collection<EventLog> currentEvents, List<EventLog> fullFilledEvents) {
        LOGGER.debug("FULLFILED : " + fullFilledEvents);
        eventLogRepository.saveAll(fullFilledEvents);

        LOGGER.debug("CURRENT : " + currentEvents);
        eventLogRepository.saveAll(currentEvents);
    }
}
