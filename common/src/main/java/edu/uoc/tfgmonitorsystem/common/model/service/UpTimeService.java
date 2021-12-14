package edu.uoc.tfgmonitorsystem.common.model.service;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import org.springframework.stereotype.Service;

@Service
public class UpTimeService implements IUpTimeService {

    private final long timestamp;

    public UpTimeService() {
        timestamp = System.currentTimeMillis();
    }

    @Override
    public int upTimeInSeconds() throws TfgMonitorSystenException {
        return (int) ((System.currentTimeMillis() - timestamp) / 1000L);
    }
}
