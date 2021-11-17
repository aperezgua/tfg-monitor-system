package edu.uoc.tfgmonitorsystem.systemmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.Country;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.systemmicroservice.model.service.ICountryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de países.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/countries")
public class CountriesController {

    /**
     * Servicio para gestionar la capa de negocio de los países.
     */
    @Autowired
    private ICountryService countryService;

    @RequestMapping(value = "/all", method = { RequestMethod.GET })
    public List<Country> getAll() throws TfgMonitorSystenException {
        return countryService.findAll();

    }
}
