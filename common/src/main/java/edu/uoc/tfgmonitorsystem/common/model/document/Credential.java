package edu.uoc.tfgmonitorsystem.common.model.document;

/**
 * Interfaz que define una credencial en el sistema sea usuario o agente.
 */
public interface Credential {

    /**
     * Identifiación de la credencial, el token para el agente y el email para el usuario.
     *
     * @return String con el asunto de la credencial.
     */
    String getSubject();

}
