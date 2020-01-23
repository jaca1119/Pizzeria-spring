package com.itvsme.pizzeria.Controller;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.Pizza;
import com.itvsme.pizzeria.Service.AddonsService;
import com.itvsme.pizzeria.Service.PizzaService;
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
    private PizzaService pizzaService;

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

    @GetMapping("/standard")
    public ResponseEntity<List<Pizza>> getAllStandardPizzas()
    {
        return new ResponseEntity<>(pizzaService.findAll(), HttpStatus.OK);
    }
}
