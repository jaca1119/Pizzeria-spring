package com.itvsme.pizzeria.Controller;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Service.AddonsService;
import com.itvsme.pizzeria.Service.ComposedPizzaService;
import com.itvsme.pizzeria.Service.StandardPizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PizzeriaController
{
    @Autowired
    private AddonsService addonsService;
    @Autowired
    private StandardPizzaService standardPizzaService;
    @Autowired
    private ComposedPizzaService composedPizzaService;

    @GetMapping("/addons")
    public ResponseEntity<List<Addon>> getAllAddons()
    {
        return new ResponseEntity<>(addonsService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/addons")
    public ResponseEntity<Addon> createAddon(@RequestBody Addon addon)
    {
        return new ResponseEntity<>(addonsService.save(addon), HttpStatus.CREATED);
    }

    @PostMapping("/composed")
    public ResponseEntity<ComposedPizza> createComposedPizza(@RequestBody ComposedPizza pizza)
    {
        return new ResponseEntity<>(composedPizzaService.save(pizza), HttpStatus.CREATED);
    }

    @GetMapping("/standard")
    public ResponseEntity<List<StandardPizza>> getAllStandardPizzas()
    {
        return new ResponseEntity<>(standardPizzaService.findAll(), HttpStatus.OK);
    }
}
