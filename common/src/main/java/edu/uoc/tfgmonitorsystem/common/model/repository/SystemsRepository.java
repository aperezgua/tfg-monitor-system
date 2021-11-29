package edu.uoc.tfgmonitorsystem.common.model.repository;

import edu.uoc.tfgmonitorsystem.common.model.document.Systems;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemsRepository extends MongoRepository<Systems, Integer> {
}
