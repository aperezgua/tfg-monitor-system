package edu.uoc.tfgmonitorsystem.logmicroservice.scheduled;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.ILogService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LogMonitor {

    private static final Logger LOGGER = Logger.getLogger(LogMonitor.class);

    /**
     * SI el monitor está habilitado.
     */
    @Value("${tfgmonitorsystem.logmonitor.enabled}")
    private Boolean enabled;

    /**
     * Nº de segundos maximos a mantener el log.
     */
    @Value("${tfgmonitorsystem.logmonitor.maxTimeInSecondsToMaintaingLog}")
    private Long maxTimeInSecondsToMaintaingLog;

    /**
     * Cada cuanto tiempo se quiere que funcione el monitor.
     */
    @Value("${tfgmonitorsystem.logmonitor.timeRunningInSeconds}")
    private Long timeRunningInSeconds;

    @Autowired
    private ILogService logService;

    /**
     * Ultima vez que se ha ejecutado.
     */
    private long lastExecutionTime = 0L;

    private AtomicBoolean executing = new AtomicBoolean(false);

    @Scheduled(fixedRate = 1000)
    public void execute() throws TfgMonitorSystenException {

        if (enabled && lastExecutionTime + timeRunningInSeconds * 1000 < System.currentTimeMillis()
                && executing.compareAndSet(false, true)) {
            LOGGER.info("Executing logMonitor: ");

            try {
                logService.pruneLog(maxTimeInSecondsToMaintaingLog);
            } finally {
                lastExecutionTime = System.currentTimeMillis();
                executing.set(false);
            }
        }

    }
}
