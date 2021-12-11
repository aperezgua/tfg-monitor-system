package edu.uoc.tfgmonitorsystem.logmicroservice.model.service;

import com.mongodb.client.result.DeleteResult;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.LogRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.AgentLogFilter;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class LogService implements ILogService {

    private static final Logger LOGGER = Logger.getLogger(LogService.class);
    /**
     * Repositorio de log.
     */
    @Autowired
    private LogRepository logRepository;
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

    @Override
    public Log createLog(Log log) throws TfgMonitorSystenException {

        log.setId(dbSequenceService.generateDbSequence(Log.SEQUENCE_NAME));
        log.setDate(new Date());

        return logRepository.save(log);
    }

    @Override
    public List<Log> findByAgent(AgentLogFilter regexpFilter) throws TfgMonitorSystenException {

        Query query = new Query(Criteria.where("agent.token").is(regexpFilter.getAgentTokenId()));
        if (regexpFilter.getRegexp() != null) {
            query.addCriteria(Criteria.where("logLine").regex(regexpFilter.getRegexp()));
        }
        if (regexpFilter.getLimitResults() != null) {
            query.limit(regexpFilter.getLimitResults());
        }
        query.with(Sort.by(Sort.Direction.DESC, "date"));

        return mongoTemplate.find(query, Log.class);
    }

    @Override
    public void pruneLog(Long maxTimeInSecondsToMaintaingLog) throws TfgMonitorSystenException {
        Query query = new Query(Criteria.where("date")
                .lt(new Date(System.currentTimeMillis() - maxTimeInSecondsToMaintaingLog * 1000L)));

        DeleteResult removed = mongoTemplate.remove(query, Log.class);

        LOGGER.info("Removed: " + removed.getDeletedCount());
    }

}
