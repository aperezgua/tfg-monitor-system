package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.SystemFilter;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los usuarios.
 */
public interface ISystemService {

    /**
     * Busca sistemas según un filtro aplicado.
     *
     * @param filter Filtro que será aplicado.
     * @return Listado de sistemas.
     */
    List<System> findByFilter(SystemFilter filter) throws TfgMonitorSystenException;

}