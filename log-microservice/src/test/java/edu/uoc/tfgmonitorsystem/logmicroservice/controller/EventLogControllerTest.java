package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import edu.uoc.tfgmonitorsystem.common.model.document.EventLog;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.EventLogFilter;
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
public class EventLogControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Se prueba la petición de búsqueda por regexp.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findLastLogEventsTest() {

        EventLogFilter filter = new EventLogFilter();

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/rest/eventLog/findLastLogEvents", filter,
                Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<EventLog> logs = (List<EventLog>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(0), Integer.valueOf(logs.size()));
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
