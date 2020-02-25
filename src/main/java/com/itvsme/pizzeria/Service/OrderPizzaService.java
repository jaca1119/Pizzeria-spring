package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Model.OrderComposedPizza;
import com.itvsme.pizzeria.Model.OrderPizza;
import com.itvsme.pizzeria.Repository.AddonRepository;
import com.itvsme.pizzeria.Repository.OrderPizzaRepository;
import com.itvsme.pizzeria.Repository.OrderStandardPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPizzaService
{
    private OrderPizzaRepository orderPizzaRepository;
    private OrderStandardPizzaRepository orderStandardPizzaRepository;
    private AddonRepository addonRepository;

    public OrderPizzaService(OrderPizzaRepository orderPizzaRepository, OrderStandardPizzaRepository orderStandardPizzaRepository, AddonRepository addonRepository)
    {
        this.orderPizzaRepository = orderPizzaRepository;
        this.orderStandardPizzaRepository = orderStandardPizzaRepository;
        this.addonRepository = addonRepository;
    }

    public OrderPizzaService(OrderPizzaRepository orderPizzaRepository)
    {
        this.orderPizzaRepository = orderPizzaRepository;
    }

    public OrderPizza saveOrder(OrderPizza orderPizza)
    {
        if (orderPizza instanceof OrderComposedPizza)
        {
            ((OrderComposedPizza) orderPizza).getComposedPizza().getAddonsInput();
        }
        return orderPizzaRepository.save(orderPizza);
    }

    public List<OrderPizza> findAll()
    {
        return orderPizzaRepository.findAll();
    }
}
