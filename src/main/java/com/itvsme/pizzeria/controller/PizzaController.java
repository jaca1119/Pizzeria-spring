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
    private ComposedPizzaService composedPizzaService;

    public PizzaController(StandardPizzaService standardPizzaService, ComposedPizzaService composedPizzaService)
    {
        this.standardPizzaService = standardPizzaService;
        this.composedPizzaService = composedPizzaService;
    }

    @GetMapping("/standard")
    public ResponseEntity<List<StandardPizza>> getAllStandardPizzas()
    {
        return ResponseEntity.ok().header("Cache-Control", "no-transform, public, max-age=3600").body(standardPizzaService.findAll());
    }

    @Transactional
    @PostMapping("/composed")
    public ResponseEntity<ComposedPizza> createComposedPizza(@RequestBody ComposedPizza pizza)
    {
        return new ResponseEntity<>(composedPizzaService.saveComposedPizza(pizza), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/standard")
    public ResponseEntity<StandardPizza> saveStandardPizza(@RequestBody StandardPizza standardPizza)
    {
        return new ResponseEntity<>(standardPizzaService.save(standardPizza), HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/standard/{id}")
    public ResponseEntity<Void> deleteStandardPizza(@PathVariable Integer id)
    {
        return new ResponseEntity<>(standardPizzaService.deleteById(id), HttpStatus.OK);
    }
}
