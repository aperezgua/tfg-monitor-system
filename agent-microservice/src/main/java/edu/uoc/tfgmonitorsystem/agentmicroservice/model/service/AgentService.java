package edu.uoc.tfgmonitorsystem.agentmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto.AgentFilter;
import edu.uoc.tfgmonitorsystem.agentmicroservice.model.exception.TokenCanNotBeNullException;
import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.exception.NoSuchElementInDbException;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.AgentRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class AgentService implements IAgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Agent createOrUpdate(Agent agent) throws TfgMonitorSystenException {

        if (agent.getToken() == null) {
            throw new TokenCanNotBeNullException("agent.token.notnull", "Token cannot be null");
        }

        if (agent.getCreatedDate() == null) {
            agent.setCreatedDate(new Date());
        }

        return agentRepository.save(agent);
    }

    @Override
    public List<Agent> findByFilter(AgentFilter filter) throws TfgMonitorSystenException {
        Query query = new Query();

        if (filter.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(".*" + filter.getName() + ".*", "i"));
        }

        if (filter.getActiveTypeFilter() != null) {
            switch (filter.getActiveTypeFilter()) {
            case ACTIVE:
                query.addCriteria(Criteria.where("active").is(Boolean.TRUE));
                break;
            case INACTIVE:
                query.addCriteria(Criteria.where("active").is(Boolean.FALSE));
                break;
            case ALL:
            default:
                break;
            }

        }

        if (filter.getSystemId() != null) {
            query.addCriteria(Criteria.where("systems.id").is(filter.getSystemId()));
        }

        return mongoTemplate.find(query, Agent.class);
    }

    @Override
    public Agent findByToken(String token) throws TfgMonitorSystenException {

        Optional<Agent> agentOptional = agentRepository.findById(token);

        if (!agentOptional.isPresent()) {
            throw new NoSuchElementInDbException("agent.notfound", "Agent not found");
        }
        return agentOptional.get();
    }

}
