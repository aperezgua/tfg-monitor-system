package edu.uoc.tfgmonitorsystem.systemmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.Systems;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.systemmicroservice.model.dto.SystemFilter;
import edu.uoc.tfgmonitorsystem.systemmicroservice.model.service.ISystemsService;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de sistemas.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/systems")
public class SystemsController {

    private static final Logger LOGGER = Logger.getLogger(SystemsController.class);

    /**
     * Servicio para gestionar la capa de negocio de los sistemas.
     */
    @Autowired
    private ISystemsService systemsService;

    /**
     * Busca sistemas según un filtro.
     *
     * @param filter UserFilter con los datos de filtrado de usuario.
     * @return Listado de usuarios que coinciden con el filtro.
     */
    @RequestMapping(value = "/find", method = { RequestMethod.POST })
    public List<Systems> find(@RequestBody SystemFilter filter) throws TfgMonitorSystenException {
        List<Systems> systems = systemsService.findByFilter(filter);
        LOGGER.debug("filter=" + filter + ", return=" + systems);
        return systems;
    }

    @GetMapping("/get/{id}")
    public Systems get(@PathVariable Integer id) throws TfgMonitorSystenException {
        Systems systems = systemsService.findById(id);
        LOGGER.debug("id=" + id + ", return=" + systems);
        return systems;
    }

    /**
     * Guarda un sistema obtenido a través de la request.
     *
     * @param systems sistema que debe ser actualizado o creado.
     * @return sistema actualizado.
     */
    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public Systems put(@Valid @RequestBody Systems systems) throws TfgMonitorSystenException {

        Systems updatedSystems = systemsService.createOrUpdate(systems);
        LOGGER.debug("user=" + systems + ", return=" + updatedSystems);
        return updatedSystems;

    }

}
