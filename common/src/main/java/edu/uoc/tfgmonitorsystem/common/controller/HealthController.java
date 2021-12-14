package edu.uoc.tfgmonitorsystem.common.controller;

import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.common.model.service.IUpTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de agentes.
 */
@RestController
@CrossOrigin
public class HealthController {

    @Autowired
    private IUpTimeService upTimeService;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/health")
    public ResponseEntity<String> health() throws TfgMonitorSystenException {
        return ResponseEntity.ok(Integer.toString(upTimeService.upTimeInSeconds()));
    }

    @GetMapping("/shutdown")
    public ResponseEntity<String> shutdown() throws TfgMonitorSystenException {
        ((ConfigurableApplicationContext) context).close();
        return ResponseEntity.ok("OK");
    }

}
