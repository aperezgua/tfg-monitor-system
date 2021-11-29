package edu.uoc.tfgmonitorsystem.systemmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Country;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los países.
 */
public interface ICountryService {

    /**
     * Busca los países.
     *
     * @return Listado de países.
     */
    List<Country> findAll() throws TfgMonitorSystenException;

}