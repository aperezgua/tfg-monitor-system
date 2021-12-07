package edu.uoc.tfgmonitorsystem.common.model.repository;

import edu.uoc.tfgmonitorsystem.common.model.document.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RuleRepository extends MongoRepository<Rule, String> {
}
