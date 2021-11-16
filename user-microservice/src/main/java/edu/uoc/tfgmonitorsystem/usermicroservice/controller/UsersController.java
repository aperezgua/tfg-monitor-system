package edu.uoc.tfgmonitorsystem.usermicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.service.IUserService;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Listado de usuarios de prueba.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/users")
public class UsersController {

    private static final Logger LOGGER = Logger.getLogger(UsersController.class);

    /**
     * Servicio para gestionar la capa de negocio de usuarios.
     */
    @Autowired
    private IUserService userService;

    /**
     * Busca usuarios según un filtro.
     * 
     * @param filter UserFilter con los datos de filtrado de usuario.
     * @return Listado de usuarios que coinciden con el filtro.
     */
    @RequestMapping(value = "/find", method = { RequestMethod.POST })
    public List<User> find(@RequestBody UserFilter filter) throws TfgMonitorSystenException {

        List<User> users = userService.findByFilter(filter);
        LOGGER.debug("filter=" + filter + ", return=" + users);
        return users;

    }

    /**
     * Guarda un usuario obtenido a través de la request.
     * 
     * @param user usuario que debe ser actualizado en el sistema.
     * @return usuario actualizado.
     */
    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public User put(@RequestBody User user) throws TfgMonitorSystenException {

        User updatedUser = userService.createOrUpdate(user);
        LOGGER.debug("user=" + user + ", return=" + updatedUser);
        return updatedUser;

    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Integer id) throws TfgMonitorSystenException {
        User user = userService.findById(id);
        LOGGER.debug("id=" + id + ", return=" + user);
        return user;
    }

    @RequestMapping(value = "/all", method = { RequestMethod.GET })
    public List<User> getAll() throws TfgMonitorSystenException {

        return userService.findAll();

    }
}
