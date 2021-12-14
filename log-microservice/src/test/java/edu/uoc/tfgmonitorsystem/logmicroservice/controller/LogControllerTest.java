package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.AgentLogFilter;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.ILogService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LogControllerTest {

    private static final String AGENT_TOKEN_ID = "0bac5204-4951-11ec-81d3-0242ac130003";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ILogService logService;

    public void createLog(String logLine) throws TfgMonitorSystenException {
        Log log = new Log();
        log.setAgent(new Agent(AGENT_TOKEN_ID));
        log.setLogLine(logLine);
        logService.createLog(log);
    }

    /**
     * Se prueba la petición de búsqueda por regexp.
     *
     * @throws TfgMonitorSystenException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findByRegexpTest() throws TfgMonitorSystenException {

        AgentLogFilter regexpFilter = new AgentLogFilter();
        regexpFilter.setAgentTokenId(AGENT_TOKEN_ID);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/rest/log/findByRegexp", regexpFilter,
                Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Log> logs = (List<Log>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(0), Integer.valueOf(logs.size()));

        createLog("Lalalla");

        response = testRestTemplate.postForEntity("/rest/log/findByRegexp", regexpFilter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        logs = (List<Log>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(1), Integer.valueOf(logs.size()));

        logService.pruneLog(0L);

    }

    @Test
    public void putLogTest() throws TfgMonitorSystenException {
        Agent agent = new Agent(AGENT_TOKEN_ID);
        testRestTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add(JwtConstants.AUTHORIZATION_HEADER, jwtTokenUtil.generateToken(agent));
            return execution.execute(request, body);
        }));

        String output = testRestTemplate.postForObject("/rest/log/put", "Nueva linea de log", String.class);

        Assertions.assertEquals("OK", output);

        for (int i = 0; i < 10; i++) {
            testRestTemplate.postForObject("/rest/log/put", "NoSuchElementException", String.class);
            Assertions.assertEquals("OK", output);
        }

        logService.pruneLog(0L);
    }

    @Test
    public void updateAgentEvents() {
        AgentLogFilter filter = new AgentLogFilter();
        filter.setAgentTokenId(AGENT_TOKEN_ID);

        ResponseEntity<String> response = testRestTemplate.postForEntity("/rest/log/updateAgentEvents", filter,
                String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals("OK", response.getBody());
    }

    /**
     * Setup de configuración de las peticiones para tener token de autenticación.
     */
    @BeforeEach
    void setup() {

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
