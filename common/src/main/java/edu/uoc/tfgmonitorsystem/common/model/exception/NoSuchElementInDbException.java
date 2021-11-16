package edu.uoc.tfgmonitorsystem.common.model.exception;

public class NoSuchElementInDbException extends TfgMonitorSystenException {

    private static final long serialVersionUID = 2401321730056668419L;

    public NoSuchElementInDbException(String key, String message) {
        super(key, message);
    }

}
