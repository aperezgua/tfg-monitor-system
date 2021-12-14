package edu.uoc.tfgmonitorsystem.common.model.service;

import edu.uoc.tfgmonitorsystem.common.model.exception.ShutdownException;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import org.springframework.stereotype.Service;

@Service
public class UpTimeService implements IUpTimeService {

    private final long timestamp;

    private boolean shutdown = false;

    public UpTimeService() {
        timestamp = System.currentTimeMillis();
    }

    @Override
    public boolean shutdown() throws TfgMonitorSystenException {
        shutdown = true;
        return shutdown;
    }

    @Override
    public int upTimeInSeconds() throws TfgMonitorSystenException {
        if (shutdown) {
            throw new ShutdownException("system.shutdown", "Receive a shutdown notification");
        }
        return (int) ((System.currentTimeMillis() - timestamp) / 1000L);
    }
}
