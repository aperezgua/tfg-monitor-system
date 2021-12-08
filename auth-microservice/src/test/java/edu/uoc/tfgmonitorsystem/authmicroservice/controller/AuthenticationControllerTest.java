package edu.uoc.tfgmonitorsystem.authmicroservice.controller;

import edu.uoc.tfgmonitorsystem.authmicroservice.model.dto.JwtRequest;
import edu.uoc.tfgmonitorsystem.authmicroservice.model.dto.JwtResponse;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assert.assertTrue(jwtTokenUtil.validateToken(response.getBody()));
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

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assert.assertTrue(jwtTokenUtil.validateToken(response.getBody().getToken()));
    }

}
