package edu.uoc.tfgmonitorsystem.common.model.repository;

import edu.uoc.tfgmonitorsystem.common.model.document.MonitorizableSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemRepository extends MongoRepository<MonitorizableSystem, Integer> {
}
