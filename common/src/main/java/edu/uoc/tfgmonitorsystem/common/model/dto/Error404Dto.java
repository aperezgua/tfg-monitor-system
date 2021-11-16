package edu.uoc.tfgmonitorsystem.common.model.dto;

import java.io.Serializable;

public class Error404Dto implements Serializable {

    private static final long serialVersionUID = -5849054926545971511L;
    private String message;

    public Error404Dto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
