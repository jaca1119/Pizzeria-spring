package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Model.OrderPizza;
import com.itvsme.pizzeria.Repository.ComposedPizzaRepository;
import com.itvsme.pizzeria.Repository.OrderPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ComposedPizzaService
{
    private ComposedPizzaRepository repository;
    private OrderPizzaRepository orderPizzaRepository;

    public ComposedPizzaService(ComposedPizzaRepository repository, OrderPizzaRepository orderPizzaRepository)
    {
        this.repository = repository;
        this.orderPizzaRepository = orderPizzaRepository;
    }

    public ComposedPizza saveComposedPizza(ComposedPizza composedPizza)
    {
        return repository.save(composedPizza);
    }

    public List<ComposedPizza> findAllComposedPizza()
    {
        List<ComposedPizza> composed = new ArrayList<>();
        repository.findAll().forEach(composed::add);
        return composed;
    }

    public OrderPizza saveOrder(OrderPizza orderPizza)
    {
        return orderPizzaRepository.save(orderPizza);
    }

    public List<OrderPizza> findAllOrderPizza()
    {
        List<OrderPizza> orderPizzas = new ArrayList<>();
        orderPizzaRepository.findAll().forEach(orderPizzas::add);
        return orderPizzas;
    }
}
