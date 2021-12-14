package edu.uoc.tfgmonitorsystem.common.model.service;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;

public interface IUpTimeService {

    /**
     * Obtiene el tiempo que el microservicio lleva levantado.
     *
     * @return int con el nº de segundos que el microservicio lleva levantado.
     * @throws TfgMonitorSystenException En caso de producirse un error.
     */
    int upTimeInSeconds() throws TfgMonitorSystenException;

}
