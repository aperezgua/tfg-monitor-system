package edu.uoc.tfgmonitorsystem.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de agentes.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/health")
public class HealthController {

    @GetMapping()
    public ResponseEntity<String> health() {

        return ResponseEntity.ok("OK");
    }

}
