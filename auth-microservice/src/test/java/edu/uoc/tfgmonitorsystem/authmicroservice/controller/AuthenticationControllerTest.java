package edu.uoc.tfgmonitorsystem.authmicroservice.controller;

import edu.uoc.tfgmonitorsystem.authmicroservice.model.dto.JwtRequest;
import edu.uoc.tfgmonitorsystem.authmicroservice.model.dto.JwtResponse;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Se prueba la autenticación de un agente.
     */
    @Test
    public void authenticateAgentTest() {

        JwtRequest request = new JwtRequest();

        request.setAgentToken("0bac5204-4951-11ec-81d3-0242ac130003");

        ResponseEntity<String> response = testRestTemplate.postForEntity("/authenticate", request, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertTrue(jwtTokenUtil.validateToken(response.getBody()));
    }

    /**
     * Se prueba la autenticación de un usuario.
     */
    @Test
    public void authenticateUserTest() {

        JwtRequest request = new JwtRequest();

        request.setUsername("aperezgua@uoc.edu");
        request.setPassword("pw");

        ResponseEntity<JwtResponse> response = testRestTemplate.postForEntity("/authenticate", request,
                JwtResponse.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertTrue(jwtTokenUtil.validateToken(response.getBody().getToken()));
    }

}
