package edu.uoc.tfgmonitorsystem.authmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;

public interface IAuthService {

    User userAuthenticate(String email, String password) throws TfgMonitorSystenException;

}
