package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import edu.uoc.tfgmonitorsystem.common.model.document.Log;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.AgentLogFilter;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LogControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Se prueba la petición de búsqueda por regexp.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findByRegexpTest() {

        AgentLogFilter regexpFilter = new AgentLogFilter();
        regexpFilter.setAgentTokenId("0bac5204-4951-11ec-81d3-0242ac130003");

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/rest/log/findByRegexp", regexpFilter,
                Object.class);

        List<Log> logs = (List<Log>) response.getBody();

        Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(logs.size()));
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
