package edu.uoc.tfgmonitorsystem.usermicroservice.resource;

import edu.uoc.tfgmonitorsystem.common.document.Users;
import edu.uoc.tfgmonitorsystem.common.repository.UserRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {

    private UserRepository userRepository;

    public UsersResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<Users> getAll() {

        return userRepository.findAll();

    }
}
