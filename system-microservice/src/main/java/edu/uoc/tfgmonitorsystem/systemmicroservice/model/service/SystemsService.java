package edu.uoc.tfgmonitorsystem.systemmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Systems;
import edu.uoc.tfgmonitorsystem.common.model.exception.NoSuchElementInDbException;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.repository.SystemsRepository;
import edu.uoc.tfgmonitorsystem.common.model.service.IDbSequenceService;
import edu.uoc.tfgmonitorsystem.systemmicroservice.model.dto.SystemFilter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class SystemsService implements ISystemsService {

    @Autowired
    private SystemsRepository systemsRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDbSequenceService dbSequenceService;

    @Override
    public Systems createOrUpdate(Systems systems) throws TfgMonitorSystenException {
        if (systems.getId() == null) {
            systems.setId(dbSequenceService.generateDbSequence(Systems.SEQUENCE_NAME));
            systems.setCreatedDate(new Date());
        }

        return systemsRepository.save(systems);
    }

    @Override
    public List<Systems> findByFilter(SystemFilter filter) throws TfgMonitorSystenException {

        Query query = new Query();

        if (filter.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(".*" + filter.getName() + ".*", "i"));
        }

        if (filter.getActiveTypeFilter() != null) {
            switch (filter.getActiveTypeFilter()) {
            case ACTIVE:
                query.addCriteria(Criteria.where("active").is(true));
                break;
            case INACTIVE:
                query.addCriteria(Criteria.where("active").is(false));
                break;
            case ALL:
            default:
                break;
            }

        }

        if (filter.getCountryId() != null) {
            query.addCriteria(Criteria.where("country.id").is(filter.getCountryId()));
        }

        List<Systems> systems = mongoTemplate.find(query, Systems.class);

        return systems;
    }

    @Override
    public Systems findById(Integer id) throws TfgMonitorSystenException {
        Optional<Systems> systemsOptional = systemsRepository.findById(id);
        if (!systemsOptional.isPresent()) {
            throw new NoSuchElementInDbException("systems.notfound", "System not found");
        }
        return systemsOptional.get();
    }

}
