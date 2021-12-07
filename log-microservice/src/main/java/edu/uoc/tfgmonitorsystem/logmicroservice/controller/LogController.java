package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.Credential;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.AgentLogFilter;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.IEventLogService;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.ILogService;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para manejo de logs en bruto por agentes y administradores.
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

    /**
     * Busca log según un filtro regexp de tal forma que pueda ser mostrado en la pantalla.
     *
     * @param regexpFilter Filtro aplicado.
     * @return List con el listado de log.
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @RequestMapping(value = "/findByRegexp", method = { RequestMethod.POST })
    public ResponseEntity<List<Log>> findByRegexp(@Valid @RequestBody AgentLogFilter regexpFilter)
            throws TfgMonitorSystenException {

        List<Log> logs = logService.findByAgent(regexpFilter);

        LOGGER.debug("findByRegexp=" + regexpFilter + " -> " + logs);
        return ResponseEntity.ok(logs);

    }

    /**
     * Guarda una línea de log proveniente de un agente.
     *
     * @param authentication para recoger los datos del agent que ha realizado la petición a través de la cabecera de
     *                       autenticación.
     *
     * @param lineLog        String con la línea de log a guardar
     * @return String con el resultado de la ejecución de la acción.
     * @throws TfgMonitorSystenException En caso de que se produzca un error.
     */
    @PreAuthorize("hasAuthority('AGENT')")
    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public String put(Authentication authentication, @RequestBody String lineLog) throws TfgMonitorSystenException {

        Credential credential = (Credential) authentication.getPrincipal();

        Agent agent = (Agent) credential;

        Log log = new Log();
        log.setAgent(agent);
        log.setLogLine(lineLog);

        LOGGER.debug("lineLog=" + lineLog);
        logService.createLog(log);

        eventLogService.processNewLog(agent.getSubject(), log);

        return "OK";
    }

    /**
     * Actualiza los eventos de un agente leyendo nuevamente todo su log y reprocesándolo.
     *
     * @param agentLogFilter Filtro aplicado para la actualización.
     * @return String con la respuesta de la actualización.
     * @throws TfgMonitorSystenException En caso de producirse un error.
     */
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @RequestMapping(value = "/updateAgentEvents", method = { RequestMethod.POST })
    public ResponseEntity<String> updateAgentEvents(@Valid @RequestBody AgentLogFilter agentLogFilter)
            throws TfgMonitorSystenException {

        eventLogService.processExistentLog(agentLogFilter.getAgentTokenId());

        return ResponseEntity.ok("OK");
    }

}
