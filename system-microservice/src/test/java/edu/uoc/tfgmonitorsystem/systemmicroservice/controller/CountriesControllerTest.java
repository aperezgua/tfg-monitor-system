package edu.uoc.tfgmonitorsystem.systemmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.Country;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CountriesControllerTest extends AbstractAdministratorController {

    /**
     * Verifica que se puede obtener e conjunto de países del microservicio.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void getAll() {
        ResponseEntity<Object> response = testRestTemplate.getForEntity("/rest/countries/all", Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Country> countries = (List<Country>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(3), countries.size());
    }
}
