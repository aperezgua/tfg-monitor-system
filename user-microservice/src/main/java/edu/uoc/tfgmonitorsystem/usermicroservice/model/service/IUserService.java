package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import java.util.List;

/**
 * Servicio que gestiona la lógica de negocio de los usuarios.
 */
public interface IUserService {

    /**
     * Crea o actualiza un usuario existente con los datos de user.
     *
     * @param user User con los datos a insertar o crear nuevos.
     * @return User con los datos actualizados.
     */
    User createOrUpdate(User user) throws TfgMonitorSystenException;

    @Deprecated
    List<User> findAll() throws TfgMonitorSystenException;

    /**
     * Busca usuarios según un filtro aplicado.
     *
     * @param filter Filtro que será aplicado.
     * @return Listado de usuairos.
     */
    List<User> findByFilter(UserFilter filter) throws TfgMonitorSystenException;

    /**
     * Busca un usuario por su id.
     *
     * @param id id del usuario que se desea obtener.
     * @return User con el usuario.
     */
    User findByIdNoPassword(Integer id) throws TfgMonitorSystenException;
}
