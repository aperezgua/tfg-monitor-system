package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import edu.uoc.tfgmonitorsystem.common.model.exception.NoSuchElementInDbException;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.UserRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.exception.EmailAlreadyExistsException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDbSequenceService dbSequenceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createOrUpdate(User user) throws TfgMonitorSystenException {

        checkDuplicatedUser(user);

        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setId(dbSequenceService.generateDbSequence(User.SEQUENCE_NAME));
            return userRepository.save(user);
        }
        User userToUpdate = findById(user.getId());

        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setName(user.getName());
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userToUpdate.setActive(user.getActive());

        return userRepository.save(userToUpdate);

    }

    @Override
    public List<User> findAll() throws TfgMonitorSystenException {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByFilter(UserFilter filter) throws TfgMonitorSystenException {

        Query query = new Query();

        if (filter.getEmail() != null) {
            query.addCriteria(Criteria.where("email").regex(".*" + filter.getEmail() + ".*", "i"));
        }

        if (filter.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(".*" + filter.getName() + ".*", "i"));
        }

        if (filter.getActiveTypeFilter() != null && !filter.getActiveTypeFilter().equals(ActiveTypeFilter.ALL)) {
            query.addCriteria(Criteria.where("activeTypeFilter").is(filter.getActiveTypeFilter()));
        }

        List<User> users = mongoTemplate.find(query, User.class);

        for (User user : users) {
            user.setPassword(null);
        }

        return users;
    }

    @Override
    public User findByIdNoPassword(Integer id) throws TfgMonitorSystenException {
        User user = findById(id);
        user.setPassword(null);
        return user;
    }

    /**
     * Verifica si un email puede ser dado de alta por el sistema.
     *
     * @param user Usuario que se quiere dar de alta
     * @throws TfgMonitorSystenException Si se produce un error en la validación.
     */
    private void checkDuplicatedUser(User user) throws TfgMonitorSystenException {
        UserFilter userFilter = new UserFilter();
        userFilter.setEmail(user.getEmail());
        List<User> usersByEmail = findByFilter(userFilter);
        if (!CollectionUtils.isEmpty(usersByEmail)) {
            for (User userEmail : usersByEmail) {
                if (user.getId() == null || !user.getId().equals(userEmail.getId())) {
                    throw new EmailAlreadyExistsException("email.alreadyexists",
                            "Email " + user.getEmail() + " already exists.");
                }
            }
        }

    }

    /**
     * Busca un usuario por id.
     *
     * @param id Integer con el id de usuario.
     * @return usuario User.
     * @throws TfgMonitorSystenException.
     */
    private User findById(Integer id) throws TfgMonitorSystenException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NoSuchElementInDbException("user.notfound", "User not found");
        }

        return userRepository.findById(id).get();
    }

}
