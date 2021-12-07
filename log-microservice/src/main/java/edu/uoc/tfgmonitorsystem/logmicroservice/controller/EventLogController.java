package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.EventLog;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.EventLogFilter;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.EventLogSummary;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.IEventLogService;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para los eventos.
 */
@RestController
@CrossOrigin
@PreAuthorize("hasAuthority('SUPPORT')")
@RequestMapping(value = "/rest/eventLog")
public class EventLogController {

    private static final Logger LOGGER = Logger.getLogger(EventLogController.class);

    @Autowired
    private IEventLogService eventLogService;

    /**
     * Realiza el resumen de un conjunto de eventos filtrados según el filtro, de tal forma que se pueda saber su nº y
     * su incremento/decremento vs su periodo anterior.
     *
     * @param eventFilter con el filtro a aplicar.
     * @return entidad EventLogSummary con los datos.
     * @throws TfgMonitorSystenException en caso de producirse un error.
     */
    @RequestMapping(value = "/findEventSummary", method = { RequestMethod.POST })
    public ResponseEntity<EventLogSummary> findEventSummary(@Valid @RequestBody EventLogFilter eventFilter)
            throws TfgMonitorSystenException {
        EventLogSummary summary = eventLogService.findEventLogSummary(eventFilter);
        LOGGER.debug("findEventSummary filter=" + eventFilter + "=" + summary);
        return ResponseEntity.ok(summary);
    }

    /**
     * Busca los últimos eventos según un filtro aplicado.
     *
     * @param eventFilter Filtro que se aplica al listado de eventos.
     * @return Listado de eventos filtrado.
     * @throws TfgMonitorSystenException
     */
    @RequestMapping(value = "/findLastLogEvents", method = { RequestMethod.POST })
    public ResponseEntity<List<EventLog>> findLastLogEvents(@Valid @RequestBody EventLogFilter eventFilter)
            throws TfgMonitorSystenException {

        List<EventLog> events = eventLogService.findLastEventLog(eventFilter);
        LOGGER.debug("findLastLogEvents filter=" + eventFilter + "=" + events);
        return ResponseEntity.ok(events);
    }
}
