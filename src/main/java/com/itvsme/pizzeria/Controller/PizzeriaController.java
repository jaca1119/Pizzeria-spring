package com.itvsme.pizzeria.Controller;

import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Repository.OrderPizzaRepository;
import com.itvsme.pizzeria.Service.AddonsService;
import com.itvsme.pizzeria.Service.ComposedPizzaService;
import com.itvsme.pizzeria.Service.OrderPizzaService;
import com.itvsme.pizzeria.Service.StandardPizzaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OrderPizzaRepository orderPizzaRepository;
    private OrderPizzaService orderPizzaService;

    @Autowired
    public PizzeriaController(AddonsService addonsService, StandardPizzaService standardPizzaService, ComposedPizzaService composedPizzaService, OrderPizzaRepository orderPizzaRepository, OrderPizzaService orderPizzaService)
    {
        this.addonsService = addonsService;
        this.standardPizzaService = standardPizzaService;
        this.composedPizzaService = composedPizzaService;
        this.orderPizzaRepository = orderPizzaRepository;
        this.orderPizzaService = orderPizzaService;
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

    @GetMapping("/orders")
    public ResponseEntity<List<OrderComposedPizza>> getAllOrderPizzas()
    {
        return new ResponseEntity<>(composedPizzaService.findAllOrderPizza(), HttpStatus.OK);
    }

    @GetMapping("/ordersStandard")
    public ResponseEntity<List<OrderStandardPizza>> getAllOrderStandardPizzas()
    {
        return new ResponseEntity<>(standardPizzaService.findAllOrdersStandard(), HttpStatus.OK);
    }

    @GetMapping("/allorders")
    public ResponseEntity<List<OrderPizza>> getAllOrders()
    {
        return new ResponseEntity<>(orderPizzaRepository.findAll(), HttpStatus.OK);
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

    @PostMapping("/orders")
    public ResponseEntity<OrderComposedPizza> createOrderPizza(@RequestBody OrderComposedPizza orderComposedPizza)
    {
        return new ResponseEntity<>(composedPizzaService.saveOrder(orderComposedPizza), HttpStatus.CREATED);
    }

    @PostMapping("/ordersStandard")
    public ResponseEntity<OrderStandardPizza> createOrderStandardPizza(@RequestBody OrderStandardPizza orderPizza)
    {
        return new ResponseEntity<>(standardPizzaService.saveOrderStandardPizza(orderPizza), HttpStatus.CREATED);
    }

    @PostMapping("/orders-pizza")
    public ResponseEntity<List<OrderPizza>> createAllOrdersPizza(@RequestBody Iterable<OrderPizza> orderPizzas)
    {
        return new ResponseEntity<>(orderPizzaService.saveAll(orderPizzas), HttpStatus.CREATED);
    }

    @PostMapping("/order-pizza-carts")
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
