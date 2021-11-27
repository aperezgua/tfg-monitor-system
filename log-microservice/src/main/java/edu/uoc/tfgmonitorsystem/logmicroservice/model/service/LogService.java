package edu.uoc.tfgmonitorsystem.logmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.LogRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService implements ILogService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private IDbSequenceService dbSequenceService;

    @Override
    public Log createLog(Log log) throws TfgMonitorSystenException {

        log.setId(dbSequenceService.generateDbSequence(User.SEQUENCE_NAME));
        log.setDate(new Date());

        return logRepository.save(log);
    }

}
