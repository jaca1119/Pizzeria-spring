package com.itvsme.pizzeria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PizzeriaController
{
    @Autowired
    private PizzeriaService pizzeriaService;

    @GetMapping("/addons")
    public ResponseEntity<List<Addon>> getAllAddons()
    {
        return new ResponseEntity<>(pizzeriaService.findAll(), HttpStatus.OK);
    }
}
