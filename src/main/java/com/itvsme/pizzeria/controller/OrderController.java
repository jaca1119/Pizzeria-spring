package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.order.OrderPizza;
import com.itvsme.pizzeria.model.order.OrderPizzaCart;
import com.itvsme.pizzeria.service.OrderPizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController
{
    private OrderPizzaService orderPizzaService;


    public OrderController(OrderPizzaService orderPizzaService)
    {
        this.orderPizzaService = orderPizzaService;
    }

    @PostMapping("/orders-pizza")
    public ResponseEntity<List<OrderPizza>> createOrdersFromList(@RequestBody List<OrderPizza> orderPizzas)
    {
        return new ResponseEntity<>(orderPizzaService.saveAll(orderPizzas), HttpStatus.CREATED);
    }

    @PostMapping("/order-pizza-cart")
    public ResponseEntity<OrderPizzaCart> createOrderPizzaCart(@RequestBody OrderPizzaCart orderPizzaCart)
    {
        return new ResponseEntity<>(orderPizzaService.saveOrderPizzaCart(orderPizzaCart), HttpStatus.CREATED);
    }
}
