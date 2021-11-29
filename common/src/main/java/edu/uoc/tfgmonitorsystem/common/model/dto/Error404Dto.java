package edu.uoc.tfgmonitorsystem.common.model.dto;

import java.io.Serializable;

public class Error404Dto implements Serializable {

    private static final long serialVersionUID = -5849054926545971511L;

    private String key;

    private String message;

    public Error404Dto(String key, String message) {
        this.message = message;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

}
