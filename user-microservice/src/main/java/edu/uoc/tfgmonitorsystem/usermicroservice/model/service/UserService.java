package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import edu.uoc.tfgmonitorsystem.common.model.exception.NoSuchElementInDbException;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.UserRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDbSequenceService dbSequenceService;

    @Override
    public List<User> findAll() throws TfgMonitorSystenException {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) throws TfgMonitorSystenException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new NoSuchElementInDbException("User not found");
        }

        return userRepository.findById(id).get();
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

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public User createOrUpdate(User user) throws TfgMonitorSystenException {

        if (user.getId() == null) {
            user.setId(dbSequenceService.generateDbSequence(User.SEQUENCE_NAME));
            return userRepository.save(user);
        } else {
            User userToUpdate = findById(user.getId());

            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setName(user.getName());

            return userRepository.save(userToUpdate);
        }

    }

}
