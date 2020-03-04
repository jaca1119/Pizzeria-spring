package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.*;
import com.itvsme.pizzeria.repository.RequestLogRepository;
import com.itvsme.pizzeria.service.AddonsService;
import com.itvsme.pizzeria.service.ComposedPizzaService;
import com.itvsme.pizzeria.service.OrderPizzaService;
import com.itvsme.pizzeria.service.StandardPizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PizzeriaController
{
    private AddonsService addonsService;
    private StandardPizzaService standardPizzaService;
    private ComposedPizzaService composedPizzaService;
    private OrderPizzaService orderPizzaService;
    private RequestLogRepository requestLogRepository;

    public PizzeriaController(AddonsService addonsService, StandardPizzaService standardPizzaService, ComposedPizzaService composedPizzaService, OrderPizzaService orderPizzaService, RequestLogRepository requestLogRepository)
    {
        this.addonsService = addonsService;
        this.standardPizzaService = standardPizzaService;
        this.composedPizzaService = composedPizzaService;
        this.orderPizzaService = orderPizzaService;
        this.requestLogRepository = requestLogRepository;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<RequestLog>> getAllLogs()
    {
        return new ResponseEntity<>(requestLogRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/addons")
    public ResponseEntity<List<Addon>> getAllAddons()
    {
        return new ResponseEntity<>(addonsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/addons/{id}")
    public ResponseEntity<Addon> getAddonById(@PathVariable Integer id)
    {
        return new ResponseEntity<>(addonsService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/standard")
    public ResponseEntity<List<StandardPizza>> getAllStandardPizzas()
    {
        return new ResponseEntity<>(standardPizzaService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/addons")
    public ResponseEntity<Addon> createAddon(@RequestBody Addon addon)
    {
        return new ResponseEntity<>(addonsService.save(addon), HttpStatus.CREATED);
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

    @PostMapping("/orders-pizza")
    public ResponseEntity<List<OrderPizza>> createAllOrdersPizza(@RequestBody Iterable<OrderPizza> orderPizzas)
    {
        return new ResponseEntity<>(orderPizzaService.saveAll(orderPizzas), HttpStatus.CREATED);
    }

    @PostMapping("/order-pizza-cart")
    public ResponseEntity<OrderPizzaCart> createOrderPizzaCart(@RequestBody OrderPizzaCart orderPizzaCart)
    {
        return new ResponseEntity<>(orderPizzaService.saveOrderPizzaCart(orderPizzaCart), HttpStatus.CREATED);
    }

    @DeleteMapping("/addons/{id}")
    public ResponseEntity<Void> deleteAddon(@PathVariable Integer id)
    {
        return new ResponseEntity<>(addonsService.deleteById(id), HttpStatus.OK);
    }

    @DeleteMapping("/standard/{id}")
    public ResponseEntity<Void> deleteStandardPizza(@PathVariable Integer id)
    {
        return new ResponseEntity<>(addonsService.deleteById(id), HttpStatus.OK);
    }
}
