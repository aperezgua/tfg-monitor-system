package edu.uoc.tfgmonitorsystem.common.model.repository;

import edu.uoc.tfgmonitorsystem.common.model.document.EventLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventLogRepository extends MongoRepository<EventLog, Integer> {
}
