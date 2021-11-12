package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import edu.uoc.tfgmonitorsystem.common.model.repository.UserRepository;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import java.util.List;
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

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByFilter(UserFilter filter) {

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
}
