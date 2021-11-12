package edu.uoc.tfgmonitorsystem.usermicroservice.view;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/find", method = { RequestMethod.POST })
    public List<User> find(@RequestBody UserFilter filter) {
        return userService.findByFilter(filter);

    }

    @RequestMapping(value = "/all", method = { RequestMethod.GET })
    public List<User> getAll() {

        return userService.findAll();

    }
}
