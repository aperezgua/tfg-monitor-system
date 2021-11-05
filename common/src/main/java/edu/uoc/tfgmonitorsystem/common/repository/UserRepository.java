package edu.uoc.tfgmonitorsystem.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.uoc.tfgmonitorsystem.common.document.Users;

public interface UserRepository extends MongoRepository<Users, Integer> {
}
