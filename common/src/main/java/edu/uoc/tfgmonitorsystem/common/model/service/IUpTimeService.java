package edu.uoc.tfgmonitorsystem.common.model.service;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;

public interface IUpTimeService {

    /**
     * Establece que el sistema devuelva una excepción al consultar el upTimeInSeconds de tal forma que docker reinicie
     * el servicio.
     *
     * @throws TfgMonitorSystenException En caso de producirse un error.
     */
    boolean shutdown() throws TfgMonitorSystenException;

    /**
     * Obtiene el tiempo que el microservicio lleva levantado.
     *
     * @return int con el nº de segundos que el microservicio lleva levantado.
     * @throws TfgMonitorSystenException En caso de producirse un error.
     */
    int upTimeInSeconds() throws TfgMonitorSystenException;

}
