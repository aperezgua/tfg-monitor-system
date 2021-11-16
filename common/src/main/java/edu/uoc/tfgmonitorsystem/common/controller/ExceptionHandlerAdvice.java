package edu.uoc.tfgmonitorsystem.common.controller;

import edu.uoc.tfgmonitorsystem.common.model.dto.Error404Dto;
import edu.uoc.tfgmonitorsystem.common.model.exception.TfgMonitorSystenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = { TfgMonitorSystenException.class })
    protected ResponseEntity<Error404Dto> exception(TfgMonitorSystenException exception, WebRequest request) {

        return new ResponseEntity<>(new Error404Dto(exception.getKey(), exception.getMessage()), HttpStatus.NOT_FOUND);

    }
}