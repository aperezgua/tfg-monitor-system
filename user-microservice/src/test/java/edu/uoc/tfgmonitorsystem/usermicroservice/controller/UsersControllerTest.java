package edu.uoc.tfgmonitorsystem.usermicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.Systems;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import edu.uoc.tfgmonitorsystem.usermicroservice.model.dto.UserFilter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsersControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Se prueba la petición de búsqueda de usuarios.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findTest() {

        UserFilter filter = new UserFilter();

        filter.setActiveTypeFilter(ActiveTypeFilter.ACTIVE);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/rest/users/find", filter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Systems> agents = (List<Systems>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(2), Integer.valueOf(agents.size()));

        // Se filtra por sam y sólo puede devolver 1
        filter.setName("Abel");

        response = testRestTemplate.postForEntity("/rest/users/find", filter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        agents = (List<Systems>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(1), Integer.valueOf(agents.size()));

        // Se filtra por inactivos
        filter.setActiveTypeFilter(ActiveTypeFilter.INACTIVE);

        response = testRestTemplate.postForEntity("/rest/users/find", filter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        agents = (List<Systems>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(0), Integer.valueOf(agents.size()));

    }

    /**
     * Setup de configuración de las peticiones para tener token de autenticación.
     */
    @BeforeAll
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
