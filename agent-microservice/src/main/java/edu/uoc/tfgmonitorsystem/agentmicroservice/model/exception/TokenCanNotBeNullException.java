package edu.uoc.tfgmonitorsystem.agentmicroservice.model.exception;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;

public class TokenCanNotBeNullException extends TfgMonitorSystenException {

    private static final long serialVersionUID = 2401321730056668419L;

    public TokenCanNotBeNullException(String key, String message) {
        super(key, message);
    }

}
