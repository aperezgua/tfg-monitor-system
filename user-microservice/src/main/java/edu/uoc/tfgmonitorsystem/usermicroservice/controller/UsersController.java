package edu.uoc.tfgmonitorsystem.usermicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
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
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/find", method = { RequestMethod.POST })
    public List<User> find(@RequestBody UserFilter filter) {

        LOGGER.debug("findByFilter: " + filter);
        List<User> users = userService.findByFilter(filter);
        LOGGER.debug("return : " + users);
        return users;

    }

    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public User put(@RequestBody User user) {

        LOGGER.debug("put: " + user);
        User updatedUser = userService.createOrUpdate(user);
        LOGGER.debug("return : " + updatedUser);
        return updatedUser;

    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @RequestMapping(value = "/all", method = { RequestMethod.GET })
    public List<User> getAll() {

        return userService.findAll();

    }
}
