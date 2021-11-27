package edu.uoc.tfgmonitorsystem.common.model.document;

/**
 * Enumerado que representa a los tipos de rol que puede ejercer un usuario.
 *
 */
public enum Rol {
    ADMINISTRATOR, SUPPORT, AGENT;

    public boolean isAgent() {
        return this.equals(AGENT);
    }

    public boolean isUser() {
        return this.equals(ADMINISTRATOR) || this.equals(SUPPORT);
    }
}
