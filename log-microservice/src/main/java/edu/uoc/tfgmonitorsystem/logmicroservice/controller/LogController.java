package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.Credential;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.AgentLogFilter;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.IEventLogService;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.ILogService;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private IEventLogService eventLogService;

    @RequestMapping(value = "/findByRegexp", method = { RequestMethod.POST })
    public List<Log> findByRegexp(@Valid @RequestBody AgentLogFilter regexpFilter) throws TfgMonitorSystenException {

        List<Log> logs = logService.findByAgent(regexpFilter);

        LOGGER.debug("findByRegexp=" + regexpFilter + " -> " + logs);
        return logs;

    }

    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public String put(Authentication authentication, @RequestBody String lineLog) throws TfgMonitorSystenException {

        Credential credential = (Credential) authentication.getPrincipal();

        if (Rol.AGENT.equals(credential.getRol())) {

            Agent agent = (Agent) credential;

            Log log = new Log();
            log.setAgent(agent);
            log.setLogLine(lineLog);

            LOGGER.debug("lineLog=" + lineLog);
            logService.createLog(log);

            eventLogService.processNewLog(agent.getSubject(), log);

            return "OK";
        }
        return "ERR";

    }

    @RequestMapping(value = "/updateAgentEvents", method = { RequestMethod.POST })
    public ResponseEntity<String> updateAgentEvents(@Valid @RequestBody AgentLogFilter agentLogFilter)
            throws TfgMonitorSystenException {

        eventLogService.processExistentLog(agentLogFilter.getAgentTokenId());

        return ResponseEntity.ok("OK");
    }
}
