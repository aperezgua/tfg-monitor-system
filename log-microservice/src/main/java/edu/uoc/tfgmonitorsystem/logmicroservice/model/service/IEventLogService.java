package edu.uoc.tfgmonitorsystem.logmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.EventLog;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.EventLogFilter;
import java.util.List;

public interface IEventLogService {

    /**
     * Busca los últimos eventos según un filtro y un límite especificado.
     *
     * @param filter Filtro que se aplica
     * @return
     * @throws TfgMonitorSystenException
     */
    List<EventLog> findLastEventLog(EventLogFilter filter) throws TfgMonitorSystenException;

    /**
     * Procesa todo el log generado por un agente desde sus inicios generando los diferentes eventos según las nuevas
     * reglas.
     *
     * @param agentTokenId String con el ID del token del agente.
     * @throws TfgMonitorSystenException En caso de producirse un error.
     */
    void processExistentLog(String agentTokenId) throws TfgMonitorSystenException;

    /**
     * Procesa una nueva línea de log agregada a un agente para generar un evento si las condiciones se dan.
     *
     * @param agentTokenId
     * @param log
     * @throws TfgMonitorSystenException
     */
    void processNewLog(String agentTokenId, Log log) throws TfgMonitorSystenException;
}
