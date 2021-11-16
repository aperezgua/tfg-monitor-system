package edu.uoc.tfgmonitorsystem.usermicroservice.model.exception;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;

public class EmailAlreadyExistsException extends TfgMonitorSystenException {

    private static final long serialVersionUID = 2401321730056668419L;

    public EmailAlreadyExistsException(String key, String message) {
        super(key, message);
    }

}
