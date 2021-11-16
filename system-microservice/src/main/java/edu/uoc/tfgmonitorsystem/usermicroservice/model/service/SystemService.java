package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.SystemRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.SystemFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class SystemService implements ISystemService {

    @Autowired
    private SystemRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDbSequenceService dbSequenceService;

    @Override
    public List<System> findByFilter(SystemFilter filter) throws TfgMonitorSystenException {

        Query query = new Query();

        if (filter.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(".*" + filter.getName() + ".*", "i"));
        }

        if (filter.getActiveTypeFilter() != null && !filter.getActiveTypeFilter().equals(ActiveTypeFilter.ALL)) {
            query.addCriteria(Criteria.where("activeTypeFilter").is(filter.getActiveTypeFilter()));
        }

        List<System> systems = mongoTemplate.find(query, System.class);

        return systems;
    }

}
