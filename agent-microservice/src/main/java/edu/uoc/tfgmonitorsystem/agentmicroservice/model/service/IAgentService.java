package edu.uoc.tfgmonitorsystem.agentmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto.AgentFilter;
import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los agentes.
 */
public interface IAgentService {

    /**
     * Crea o actualiza un agente en la base de datos.
     *
     * @param agent Agente que va a aser creado / actualizado.
     * @return agente actualizado
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    Agent createOrUpdate(Agent agent) throws TfgMonitorSystenException;

    /**
     * Busca agentes según un filtro aplicado.
     *
     * @param filter Filtro que será aplicado.
     * @return Listado de agentes.
     */
    List<Agent> findByFilter(AgentFilter filter) throws TfgMonitorSystenException;

    /**
     * Busca un agente por su token.
     *
     * @param token Token único del agente que se quiere obtener.
     * @return entidad Agent.
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    Agent findByToken(String token) throws TfgMonitorSystenException;

}