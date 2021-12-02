package edu.uoc.tfgmonitorsystem.logmicroservice.controller;

import edu.uoc.tfgmonitorsystem.common.model.document.EventLog;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.dto.EventLogFilter;
import edu.uoc.tfgmonitorsystem.logmicroservice.model.service.IEventLogService;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/rest/eventLog")
public class EventLogController {

    private static final Logger LOGGER = Logger.getLogger(EventLogController.class);

    @Autowired
    private IEventLogService eventLogService;

    @RequestMapping(value = "/findLastLogEvents", method = { RequestMethod.POST })
    public ResponseEntity<List<EventLog>> findLastLogEvents(@Valid @RequestBody EventLogFilter eventFilter)
            throws TfgMonitorSystenException {

        List<EventLog> events = eventLogService.findLastEventLog(eventFilter);
        LOGGER.debug("findLastLogEvents filter=" + eventFilter + "=" + events);
        return ResponseEntity.ok(events);
    }
}
