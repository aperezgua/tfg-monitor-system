package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Listado de usuarios de prueba.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/rest/log")
public class LogController {

    private static final Logger LOGGER = Logger.getLogger(LogController.class);

    @RequestMapping(value = "/put", method = { RequestMethod.POST })
    public String put(@RequestBody String lineLog) throws TfgMonitorSystenException {

        LOGGER.debug("lineLog=" + lineLog);
        return "OK";

    }

}
