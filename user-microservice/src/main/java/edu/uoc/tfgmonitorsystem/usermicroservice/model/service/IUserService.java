package edu.uoc.tfgmonitorsystem.usermicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import java.util.List;

public interface IUserService {

    List<User> findAll();

}
