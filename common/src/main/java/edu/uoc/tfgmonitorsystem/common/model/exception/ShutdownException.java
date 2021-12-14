package edu.uoc.tfgmonitorsystem.common.model.exception;

public class ShutdownException extends TfgMonitorSystenException {

    private static final long serialVersionUID = -1394888968280095386L;

    public ShutdownException(String key, String message) {
        super(key, message);
    }

}
