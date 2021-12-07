package edu.uoc.tfgmonitorsystem.agentmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.controller.security.JwtConstants;
import edu.uoc.tfgmonitorsystem.common.controller.security.JwtTokenUtil;
import edu.uoc.tfgmonitorsystem.common.model.document.Rol;
import edu.uoc.tfgmonitorsystem.common.model.document.User;
import java.util.Collections;
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
//@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class AgentsControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void findTest() throws Exception {

        ResponseEntity<String> result = testRestTemplate.getForEntity("/rest/agents/find", String.class);

        System.out.println("Forentity" + result);

//        // Assert against a `.json` file in the same package as the test
//        assertThat(this.json.write(details)).isEqualToJson("expected.json");
//        // Or use JSON path based assertions
//        assertThat(this.json.write(details)).hasJsonPathStringValue("@.make");
//        assertThat(this.json.write(details)).extractingJsonPathStringValue("@.make")
//                .isEqualTo("Honda");
    }

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
