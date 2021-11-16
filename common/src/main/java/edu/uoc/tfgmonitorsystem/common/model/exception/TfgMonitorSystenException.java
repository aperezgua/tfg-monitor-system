package edu.uoc.tfgmonitorsystem.common.model.exception;

/**
 * Excepción por defecto del sistema.
 */
public class TfgMonitorSystenException extends Exception {

    private static final long serialVersionUID = -6976045271634074629L;

    private final String key;

    public TfgMonitorSystenException(String key, String message) {
        super(message);
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
