package edu.uoc.tfgmonitorsystem.usermicroservice.view;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/users")
public class UsersController {

    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public List<User> getAll() {

        return userService.findAll();

    }
}
