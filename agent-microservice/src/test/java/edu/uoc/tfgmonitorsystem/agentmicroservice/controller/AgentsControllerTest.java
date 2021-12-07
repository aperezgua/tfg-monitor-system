package edu.uoc.tfgmonitorsystem.agentmicroservice.controller;

import edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto.AgentFilter;
import edu.uoc.tfgmonitorsystem.agentmicroservice.model.dto.AgentWithLastNotificationData;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.Systems;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
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
public class AgentsControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Se prueba la petición de ultimas notificaciones de un agente.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findLastNotificationDataTest() {
        ResponseEntity<Object> response = testRestTemplate.getForEntity("/rest/agents/findLastNotificationData",
                Object.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<AgentWithLastNotificationData> agents = (List<AgentWithLastNotificationData>) response.getBody();

        Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(agents.size()));
    }

    /**
     * Se prueba una búsqueda siple de agentes.
     *
     * @throws Exception en caso de que se produzca un error.
     */
    @SuppressWarnings("unchecked")
    @Test
    @AfterAll
    public void findSimpleTest() {

        AgentFilter filter = new AgentFilter();

        filter.setActiveTypeFilter(ActiveTypeFilter.ACTIVE);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/rest/agents/find", filter, Object.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Agent> agents = (List<Agent>) response.getBody();

        Assert.assertEquals(Integer.valueOf(3), Integer.valueOf(agents.size()));

        // Se filtra por sam y sólo puede devolver 1
        filter.setName("Agente 1");

        response = testRestTemplate.postForEntity("/rest/agents/find", filter, Object.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        agents = (List<Agent>) response.getBody();

        Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(agents.size()));

        // Se filtra por inactivos
        filter.setActiveTypeFilter(ActiveTypeFilter.INACTIVE);

        response = testRestTemplate.postForEntity("/rest/agents/find", filter, Object.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        agents = (List<Agent>) response.getBody();

        Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(agents.size()));

    }

    /**
     * Verifica la generación de tokens aleatorios para agente.
     */
    @Test
    public void generateTokenTest() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/rest/agents/generateToken", String.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String token1 = response.getBody();

        response = testRestTemplate.getForEntity("/rest/agents/generateToken", String.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        String token2 = response.getBody();

        Assert.assertNotEquals(token1, token2);
    }

    /**
     * Verifica que se puede obtener un agente de mongoDB.
     */
    @Test
    public void getTest() {
        ResponseEntity<Agent> response = testRestTemplate
                .getForEntity("/rest/agents/get/0bac5204-4951-11ec-81d3-0242ac130003", Agent.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Agent agent = response.getBody();

        Assert.assertEquals("0bac5204-4951-11ec-81d3-0242ac130003", agent.getToken());
    }

    /**
     * Se realiza el guardado de un nuevo agent en el sistema.
     */
    @Test
    public void putTest() {
        Agent agentToSave = new Agent();
        agentToSave.setToken("aaaaa");
        agentToSave.setName("Blablabla");
        agentToSave.setActive(Boolean.TRUE);
        Systems systems = new Systems();
        systems.setId(10001);
        agentToSave.setSystems(systems);

        ResponseEntity<Agent> response = testRestTemplate.postForEntity("/rest/agents/put", agentToSave, Agent.class);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

        Agent agent = response.getBody();

        Assert.assertEquals("aaaaa", agent.getToken());
    }

    /**
     * Setup de configuración de las peticiones para tener token de autenticación.
     */
    @Before
    public void setup() {

        User user = new User();
        user.setName("Admin");
        user.setRol(Rol.ADMINISTRATOR);
        user.setEmail("a@b.com");

        testRestTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add(JwtConstants.AUTHORIZATION_HEADER, jwtTokenUtil.generateToken(user));
            return execution.execute(request, body);
        }));
    }
}
