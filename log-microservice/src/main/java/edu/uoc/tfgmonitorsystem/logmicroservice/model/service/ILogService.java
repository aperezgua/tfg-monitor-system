package edu.uoc.tfgmonitorsystem.logmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.AgentLogFilter;
import java.util.List;

public interface ILogService {

    /**
     * Crea una nueva linea de log en persistencia
     *
     * @param log Log con la nueva linea de log.
     * @return Log con el log persistido.
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    Log createLog(Log log) throws TfgMonitorSystenException;

    /**
     * Busca líneas de log para una expresión regular y un token de agente en concreto.
     *
     * @param regexpFilter RegexpFilter con el filtro a aplicar.
     * @return Listado de log.
     * @throws TfgMonitorSystenException En caso de producirse un erro.
     */
    List<Log> findByAgent(AgentLogFilter regexpFilter) throws TfgMonitorSystenException;

    /**
     * Vacía el log para liberar espacio y que los procesos no tengan que calcular eventos con millones de tuplas.
     *
     * @param maxTimeInSecondsToMaintaingLog Long con el tiempo en segundos a mantener el log desde la fecha.
     * @throws TfgMonitorSystenException en caso de que se produzca un error.
     */
    void pruneLog(Long maxTimeInSecondsToMaintaingLog) throws TfgMonitorSystenException;

}
