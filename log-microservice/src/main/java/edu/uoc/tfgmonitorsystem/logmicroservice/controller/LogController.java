package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.ILogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Listado de usuarios de prueba.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/log")
public class LogController {

    private static final Logger LOGGER = Logger.getLogger(LogController.class);

    @Autowired
    private ILogService logService;

    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public String put(Authentication authentication, @RequestBody String lineLog) throws TfgMonitorSystenException {

        Agent credential = (Agent) authentication.getPrincipal();

        Log log = new Log();
        log.setAgent(credential);
        log.setLogLine(lineLog);

        LOGGER.debug("lineLog=" + lineLog);
        logService.createLog(log);

        return "OK";

    }

}
