package edu.uoc.tfgmonitorsystem.systemmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.Country;
import edu.uoc.tfgmonitorsystem.common.model.document.Systems;
import edu.uoc.tfgmonitorsystem.common.model.dto.ActiveTypeFilter;
import edu.uoc.tfgmonitorsystem.systemmicroservice.model.dto.SystemFilter;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SystemsControllerTest extends AbstractAdministratorController {

    /**
     * Se prueba la petición de búsqueda de sistemas.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void findTest() {

        SystemFilter filter = new SystemFilter();

        filter.setActiveTypeFilter(ActiveTypeFilter.ACTIVE);

        ResponseEntity<Object> response = testRestTemplate.postForEntity("/rest/systems/find", filter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Systems> agents = (List<Systems>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(3), Integer.valueOf(agents.size()));

        // Se filtra por sam y sólo puede devolver 1
        filter.setName("Sistema 1");

        response = testRestTemplate.postForEntity("/rest/systems/find", filter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        agents = (List<Systems>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(1), Integer.valueOf(agents.size()));

        // Se filtra por inactivos
        filter.setActiveTypeFilter(ActiveTypeFilter.INACTIVE);

        response = testRestTemplate.postForEntity("/rest/systems/find", filter, Object.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        agents = (List<Systems>) response.getBody();

        Assertions.assertEquals(Integer.valueOf(0), Integer.valueOf(agents.size()));

    }

    /**
     * Verifica que se puede obtener un sistema del microservicio.
     */
    @Test
    public void getTest() {
        ResponseEntity<Systems> response = testRestTemplate.getForEntity("/rest/systems/get/1", Systems.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Systems systems = response.getBody();

        Assertions.assertEquals(Integer.valueOf(1), systems.getId());
    }

    /**
     * Se realiza el guardado de un nuevo systems en el sistema.
     */
    @Test
    public void putTest() {
        Systems systemsToSave = new Systems();
        systemsToSave.setName("Blablabla");
        systemsToSave.setActive(Boolean.TRUE);
        Country country = new Country();
        country.setId(1);
        systemsToSave.setCountry(country);

        ResponseEntity<Systems> response = testRestTemplate.postForEntity("/rest/systems/put", systemsToSave,
                Systems.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Systems systems = response.getBody();

        Assertions.assertEquals("Blablabla", systems.getName());
    }

}
