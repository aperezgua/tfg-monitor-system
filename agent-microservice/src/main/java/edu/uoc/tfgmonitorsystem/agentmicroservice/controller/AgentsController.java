package edu.uoc.tfgmonitorsystem.agentmicroservice.controller;

import edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto.AgentFilter;
import edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto.AgentWithLastNotificationData;
import edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto.TokenDto;
import edu.uoc.tfgmonitorsystem.agentmicroservice.model.service.IAgentService;
import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de agentes.
 */
@RestController
@CrossOrigin
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
@RequestMapping(value = "/rest/agents")
public class AgentsController {

    private static final Logger LOGGER = Logger.getLogger(AgentsController.class);

    /**
     * Servicio para gestionar la capa de negocio de los agentes.
     */
    @Autowired
    private IAgentService agentService;

    /**
     * Busca agentes según un filtro.
     *
     * @param filter AgentFilter con los datos de filtrado de agent.
     * @return Listado de agentes que coinciden con el filtro.
     */
    @RequestMapping(value = "/find", method = { RequestMethod.POST })
    public ResponseEntity<List<Agent>> find(@RequestBody AgentFilter filter) throws TfgMonitorSystenException {
        List<Agent> agents = agentService.findByFilter(filter);
        LOGGER.debug("filter=" + filter + ", return=" + agents);
        return ResponseEntity.ok(agents);
    }

    /**
     * Obtiene los agentes con notificaciones de log.
     *
     * @param filter UserFilter con los datos de filtrado de usuario.
     * @return Listado de usuarios que coinciden con el filtro.
     */
    @RequestMapping(value = "/findLastNotificationData", method = { RequestMethod.GET })
    public ResponseEntity<List<AgentWithLastNotificationData>> findLastNotificationData()
            throws TfgMonitorSystenException {
        List<AgentWithLastNotificationData> agents = agentService.findLastNotificationData();

        LOGGER.debug("return=" + agents);

        return ResponseEntity.ok(agents);
    }

    /**
     * Genera un token aleatorio.
     *
     * @return String con el token aleatorio.
     */
    @GetMapping("/generateToken")
    public ResponseEntity<TokenDto> generateToken() {
        String token = UUID.randomUUID().toString();
        LOGGER.debug("token=" + token);
        return ResponseEntity.ok(new TokenDto(token));
    }

    /**
     * Obtiene un agente por su token
     *
     * @param token Con el token del agente.
     * @return Agente que coincide con el token.
     * @throws TfgMonitorSystenException
     */
    @GetMapping("/get/{token}")
    public ResponseEntity<Agent> get(@PathVariable String token) throws TfgMonitorSystenException {
        Agent agent = agentService.findByToken(token);
        LOGGER.debug("token=" + token + ", return=" + agent);
        return ResponseEntity.ok(agent);
    }

    /**
     * Guarda un agente obtenido a través de la request.
     *
     * @param agent agente que debe ser actualizado o creado.
     * @return agent actualizado.
     */
    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public ResponseEntity<Agent> put(@Valid @RequestBody Agent agent) throws TfgMonitorSystenException {

        Agent updatedAgent = agentService.createOrUpdate(agent);
        LOGGER.debug("agent=" + agent + ", return=" + updatedAgent);
        return ResponseEntity.ok(updatedAgent);
    }

}
