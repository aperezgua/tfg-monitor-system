package edu.uoc.tfgmonitorsystem.authmicroservice.controller;

import edu.uoc.tfgmonitorsystem.authmicroservice.model.dto.JwtRequest;
import edu.uoc.tfgmonitorsystem.authmicroservice.model.dto.JwtResponse;
import edu.uoc.tfgmonitorsystem.authmicroservice.model.service.IAuthService;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller principal encargado de realizar la autenticación del ususario con crossorigin (multiples sites).
 */
@RestController
@CrossOrigin
public class AuthenticationController {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IAuthService authService;

    /**
     * Autentica un agente o un usuario con rol de administrador o de agente.
     *
     * @param authenticationRequest Request usada.
     * @return String o JwtResponse dependiendo de si es un agente o un usuario.
     * @throws Exception En caso de producirse un error.
     */
    @RequestMapping(value = JwtConstants.AUTHORIZATION_URL, method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest authenticationRequest) throws Exception {

        final String token;
        if (authenticationRequest.getAgentToken() != null) {
            LOGGER.debug("getAgentToken: " + authenticationRequest.getAgentToken());
            token = jwtTokenUtil.generateToken(authService.agentAuthenticate(authenticationRequest.getAgentToken()));
            return ResponseEntity.ok(token);
        }

        LOGGER.debug("Login: " + authenticationRequest.getUsername());
        token = jwtTokenUtil.generateToken(
                authService.userAuthenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        return ResponseEntity.ok(new JwtResponse(token));

    }

}
