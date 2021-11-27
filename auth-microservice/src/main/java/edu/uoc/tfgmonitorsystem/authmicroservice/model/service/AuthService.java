package edu.uoc.tfgmonitorsystem.authmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.NoSuchElementInDbException;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Agent agentAuthenticate(String token) throws TfgMonitorSystenException {
        Query query = new Query(Criteria.where("token").is(token));
        query.addCriteria(Criteria.where("active").is(true));

        Agent agent = mongoTemplate.findOne(query, Agent.class);

        if (agent == null) {
            throw new NoSuchElementInDbException("agent.notfound", "Agent not found");
        }
        return agent;
    }

    @Override
    public User userAuthenticate(String email, String password) throws TfgMonitorSystenException {

        Query query = new Query(Criteria.where("email").is(email));

        User user = mongoTemplate.findOne(query, User.class);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new NoSuchElementInDbException("user.notfound", "User not found");
        }
        return user;
    }
}
