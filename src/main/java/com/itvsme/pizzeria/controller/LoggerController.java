package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.RequestLog;
import com.itvsme.pizzeria.repository.RequestLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoggerController
{
    private RequestLogRepository requestLogRepository;

    public LoggerController(RequestLogRepository requestLogRepository)
    {
        this.requestLogRepository = requestLogRepository;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<RequestLog>> getAllLogs()
    {
        return new ResponseEntity<>(requestLogRepository.findAll(), HttpStatus.OK);
    }
}
