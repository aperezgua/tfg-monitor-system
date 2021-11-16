package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import java.util.List;

public interface IUserService {

    @Deprecated
    List<User> findAll();

    /**
     * Busca usuarios según un filtro aplicado.
     * 
     * @param filter Filtro que será aplicado.
     * @return Listado de usuairos.
     */
    List<User> findByFilter(UserFilter filter);

    User findById(Integer id);

    User createOrUpdate(User user);
}
