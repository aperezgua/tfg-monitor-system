package edu.uoc.tfgmonitorsystem.common.model.repository;

import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, Integer> {
}
