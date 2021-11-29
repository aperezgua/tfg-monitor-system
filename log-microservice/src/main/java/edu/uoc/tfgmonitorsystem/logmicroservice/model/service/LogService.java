package edu.uoc.tfgmonitorsystem.logmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.LogRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.RegexpFilter;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class LogService implements ILogService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private IDbSequenceService dbSequenceService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Log createLog(Log log) throws TfgMonitorSystenException {

        log.setId(dbSequenceService.generateDbSequence(User.SEQUENCE_NAME));
        log.setDate(new Date());

        return logRepository.save(log);
    }

    @Override
    public List<Log> findByRegexp(RegexpFilter regexpFilter) throws TfgMonitorSystenException {

        Query query = new Query(Criteria.where("agent.token").is(regexpFilter.getAgentTokenId()));
        query.addCriteria(Criteria.where("logLine").regex(regexpFilter.getRegexp()));
        query.limit(10);
        query.with(Sort.by(Sort.Direction.DESC, "date"));

        return mongoTemplate.find(query, Log.class);
    }

}
