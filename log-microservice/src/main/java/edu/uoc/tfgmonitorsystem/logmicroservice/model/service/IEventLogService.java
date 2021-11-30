package edu.uoc.tfgmonitorsystem.logmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;

public interface IEventLogService {

    void processExistentLog(String agentTokenId) throws TfgMonitorSystenException;
}
