package edu.uoc.tfgmonitorsystem.systemmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Systems;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.systemmicroservice.model.dto.SystemFilter;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los usuarios.
 */
public interface ISystemsService {

    /**
     * Crea o actualiza un sistema en la base de datos.
     *
     * @param systems Sistema que va a aser creado / actualizado.
     * @return sistema actualizado
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    Systems createOrUpdate(Systems systems) throws TfgMonitorSystenException;

    /**
     * Busca sistemas según un filtro aplicado.
     *
     * @param filter Filtro que será aplicado.
     * @return Listado de sistemas.
     */
    List<Systems> findByFilter(SystemFilter filter) throws TfgMonitorSystenException;

    /**
     * Busca un sistema por su id.
     *
     * @param id Id del sistema que se quiere obtener.
     * @return entidad Systems.
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    Systems findById(Integer id) throws TfgMonitorSystenException;

}