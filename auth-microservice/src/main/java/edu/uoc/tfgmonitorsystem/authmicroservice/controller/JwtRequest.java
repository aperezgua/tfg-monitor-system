package edu.uoc.tfgmonitorsystem.authmicroservice.controller;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String agentToken;
    private String password;
    private String username;

    public JwtRequest() {
        super();
    }

    public String getAgentToken() {
        return agentToken;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setAgentToken(String agentToken) {
        this.agentToken = agentToken;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}