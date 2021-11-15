package edu.uoc.tfgmonitorsystem.common.model.repository;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
}
