package edu.uoc.tfgmonitorsystem.authmicroservice.model.service;

import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;

public interface IAuthService {

    /**
     * Autentica a un agente a partir de su token y devuelve sus datos.
     *
     * @param token id del token del agente
     * @return Objeto Agent
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    Agent agentAuthenticate(String token) throws TfgMonitorSystenException;

    /**
     * Autentica un usuario a partir de su email y password.
     *
     * @param email    String con el email.
     * @param password String con el password
     * @return User con el usuario autenticado si los datos han coincidido.
     * @throws TfgMonitorSystenException En caso de producirse un error.
     */
    User userAuthenticate(String email, String password) throws TfgMonitorSystenException;

}
