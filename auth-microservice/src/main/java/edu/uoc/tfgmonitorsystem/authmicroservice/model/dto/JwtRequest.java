package edu.uoc.tfgmonitorsystem.authmicroservice.model.dto;

/**
 * Petición de autenticación para un agente o usuario.
 */
public class JwtRequest {

    /**
     * Token de agente para hacer login.
     */
    private String agentToken;

    /**
     * Password usado por el usuario.
     */
    private String password;

    /**
     * Nombre del usaurio.
     */
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