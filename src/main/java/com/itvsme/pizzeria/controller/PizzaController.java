package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.pizza.ComposedPizza;
import com.itvsme.pizzeria.model.pizza.StandardPizza;
import com.itvsme.pizzeria.service.ComposedPizzaService;
import com.itvsme.pizzeria.service.StandardPizzaService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
public class PizzaController
{
    private StandardPizzaService standardPizzaService;

    public PizzaController(StandardPizzaService standardPizzaService)
    {
        this.standardPizzaService = standardPizzaService;
    }

    @GetMapping("/standard")
    public ResponseEntity<List<StandardPizza>> getAllStandardPizzas()
    {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS)).body(standardPizzaService.findAll());
    }

    @Transactional
    @PostMapping("/standard")
    public ResponseEntity<StandardPizza> saveStandardPizza(@RequestBody StandardPizza standardPizza)
    {
        return new ResponseEntity<>(standardPizzaService.save(standardPizza), HttpStatus.CREATED);
    }

}
