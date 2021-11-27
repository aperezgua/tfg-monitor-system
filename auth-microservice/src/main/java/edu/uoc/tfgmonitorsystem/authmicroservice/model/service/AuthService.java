package edu.uoc.tfgmonitorsystem.authmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.NoSuchElementInDbException;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User userAuthenticate(String email, String password) throws TfgMonitorSystenException {

        Query query = new Query(Criteria.where("email").is(email));
        // query.addCriteria(Criteria.where("password").is(passwordEncoder.encode(password)));

        User user = mongoTemplate.findOne(query, User.class);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new NoSuchElementInDbException("user.notfound", "User not found");
        }
        return user;
    }
}
