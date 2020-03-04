package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.*;
import com.itvsme.pizzeria.service.ComposedPizzaService;
import com.itvsme.pizzeria.service.StandardPizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PizzaController
{
    private StandardPizzaService standardPizzaService;
    private ComposedPizzaService composedPizzaService;

    public PizzaController(StandardPizzaService standardPizzaService, ComposedPizzaService composedPizzaService)
    {
        this.standardPizzaService = standardPizzaService;
        this.composedPizzaService = composedPizzaService;
    }

    @GetMapping("/standard")
    public ResponseEntity<List<StandardPizza>> getAllStandardPizzas()
    {
        return new ResponseEntity<>(standardPizzaService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/composed")
    public ResponseEntity<ComposedPizza> createComposedPizza(@RequestBody ComposedPizza pizza)
    {
        return new ResponseEntity<>(composedPizzaService.saveComposedPizza(pizza), HttpStatus.CREATED);
    }

    @PostMapping("/standard")
    public ResponseEntity<StandardPizza> saveStandardPizza(@RequestBody StandardPizza standardPizza)
    {
        return new ResponseEntity<>(standardPizzaService.save(standardPizza), HttpStatus.CREATED);
    }

    @DeleteMapping("/standard/{id}")
    public ResponseEntity<Void> deleteStandardPizza(@PathVariable Integer id)
    {
        return new ResponseEntity<>(standardPizzaService.deleteById(id), HttpStatus.OK);
    }
}
