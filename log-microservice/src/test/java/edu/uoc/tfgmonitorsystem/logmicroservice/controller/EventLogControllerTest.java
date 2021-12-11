package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import edu.uoc.tfgmonitorsystem.common.model.document.Agent;
import edu.uoc.tfgmonitorsystem.common.model.document.EventLog;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.Severity;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.repository.EventLogRepository;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.EventLogFilter;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.EventLogSummary;
import java.util.Collections;
import java.util.Date;
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
public class EventLogControllerTest {

    private static final String AGENT_TOKEN_ID = "0bac5204-4951-11ec-81d3-0242ac130003";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventLogRepository eventLogRespository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Se prueba la petición de búsqueda por regexp.
     */
    @Test
    public void findEventSummaryTesst() {

        EventLogFilter filter = new EventLogFilter();
        filter.setSeverity(Severity.CRITICAL);

        ResponseEntity<EventLogSummary> response = testRestTemplate.postForEntity("/rest/eventLog/findEventSummary",
                filter, EventLogSummary.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals(Integer.valueOf(0), response.getBody().getNumber());

        createEventLog(1000);

        response = testRestTemplate.postForEntity("/rest/eventLog/findEventSummary", filter, EventLogSummary.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals(Integer.valueOf(1), response.getBody().getNumber());

        removeEventLog(1000);
    }

    /**
     * Se prueba la petición de búsqueda por regexp.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findLastLogEventsTest() {

        EventLogFilter filter = new EventLogFilter();
        filter.setAgentTokenId(null);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/rest/eventLog/findLastLogEvents", filter,
                Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<EventLog> logs = (List<EventLog>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(0), Integer.valueOf(logs.size()));

        createEventLog(1000);

        response = testRestTemplate.postForEntity("/rest/eventLog/findLastLogEvents", filter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        logs = (List<EventLog>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(1), Integer.valueOf(logs.size()));

        removeEventLog(1000);
    }

    /**
     * Crea un evento con un id en concreto.
     *
     * @param id
     */
    private void createEventLog(Integer id) {

        EventLog eventLog = new EventLog();
        eventLog.setId(id);
        eventLog.setAgent(new Agent(AGENT_TOKEN_ID));
        eventLog.setInitDate(new Date());
        eventLog.setDate(new Date());
        eventLog.setFullFilled(true);
        eventLog.setSeverity(Severity.CRITICAL);
        eventLog.setRuleName("Tiempos elevados");
        eventLogRespository.save(eventLog);
    }

    /**
     * Elimina un evento para no afectar al resto de elementos del sistema.
     *
     * @param id Integer con el id
     */
    private void removeEventLog(Integer id) {
        eventLogRespository.deleteById(id);
    }

    /**
     * Setup de configuración de las peticiones para tener token de autenticación.
     */
    @BeforeEach
    void setup() {

        User user = new User();
        user.setName("Support");
        user.setRol(Rol.SUPPORT);
        user.setEmail("c@d.com");

        testRestTemplate.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add(JwtConstants.AUTHORIZATION_HEADER, jwtTokenUtil.generateToken(user));
            return execution.execute(request, body);
        }));
    }
}
