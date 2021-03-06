package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.order.OrderPizza;
import com.itvsme.pizzeria.model.order.OrderPizzaCart;
import com.itvsme.pizzeria.service.OrderPizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class OrderController
{
    private OrderPizzaService orderPizzaService;


    public OrderController(OrderPizzaService orderPizzaService)
    {
        this.orderPizzaService = orderPizzaService;
    }

    @GetMapping("/all-orders")
    public ResponseEntity<List<OrderPizza>> getAllOrders()
    {
        return new ResponseEntity<>(orderPizzaService.findAll(), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/order-pizza-cart")
    public ResponseEntity<OrderPizzaCart> createOrderPizzaCart(@RequestBody OrderPizzaCart orderPizzaCart)
    {
        return new ResponseEntity<>(orderPizzaService.saveOrderPizzaCart(orderPizzaCart), HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping("/order-pizza-cart/payment/{id}")
    public ResponseEntity<Void> acceptPaymentForOrder(@PathVariable int id)
    {
        boolean isAcceptSuccessful = orderPizzaService.acceptPaymentForOrder(id);

        if (isAcceptSuccessful)
        {
            return ResponseEntity.ok().build();
        }
        else
        {
            return ResponseEntity.badRequest().build();
        }
    }
}
