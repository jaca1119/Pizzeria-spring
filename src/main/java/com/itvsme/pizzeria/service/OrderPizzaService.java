package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.*;
import com.itvsme.pizzeria.repository.AddonRepository;
import com.itvsme.pizzeria.repository.OrderPizzaCartRepository;
import com.itvsme.pizzeria.repository.OrderPizzaRepository;
import com.itvsme.pizzeria.repository.StandardPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderPizzaService
{
    private OrderPizzaRepository orderPizzaRepository;
    private StandardPizzaRepository standardPizzaRepository;
    private AddonRepository addonRepository;
    private OrderPizzaCartRepository orderPizzaCartRepository;

    @Autowired
    public OrderPizzaService(OrderPizzaRepository orderPizzaRepository, StandardPizzaRepository standardPizzaRepository, AddonRepository addonRepository, OrderPizzaCartRepository orderPizzaCartRepository)
    {
        this.orderPizzaRepository = orderPizzaRepository;
        this.standardPizzaRepository = standardPizzaRepository;
        this.addonRepository = addonRepository;
        this.orderPizzaCartRepository = orderPizzaCartRepository;
    }

    public OrderPizzaService(OrderPizzaRepository orderPizzaRepository)
    {
        this.orderPizzaRepository = orderPizzaRepository;
    }

    public OrderPizza saveOrder(OrderPizza orderPizza)
    {
        if (orderPizza instanceof OrderComposedPizza)
        {
            Set<AddonInput> addonsInput = ((OrderComposedPizza) orderPizza).getComposedPizza().getAddonsInput();
            addonsInput.forEach(addonInput -> {
                Optional<Addon> optionalAddon = addonRepository.findByName(addonInput.getAddon().getName());

                addonInput.setAddon(optionalAddon.orElse(addonInput.getAddon()));
            });
        }
        else if (orderPizza instanceof OrderStandardPizza)
        {
            Optional<StandardPizza> optionalStandardPizza = standardPizzaRepository.findByName(((OrderStandardPizza) orderPizza).getStandardPizza().getName());

            optionalStandardPizza.ifPresent(((OrderStandardPizza) orderPizza)::setStandardPizza);
        }

        return orderPizzaRepository.save(orderPizza);
    }

    public List<OrderPizza> findAll()
    {
        return orderPizzaRepository.findAll();
    }

    public List<OrderPizza> saveAll(Iterable<OrderPizza> ordersPizza)
    {
        return orderPizzaRepository.saveAll(ordersPizza);
    }

    public OrderPizzaCart saveOrderPizzaCart(OrderPizzaCart orderPizzaCart)
    {
        orderPizzaCart.getPizzas().forEach(pizza -> {
            if (pizza instanceof ComposedPizza)
            {
                Set<AddonInput> addonsInput = ((ComposedPizza) pizza).getAddonsInput();
                addonsInput.forEach(addonInput -> {
                    Optional<Addon> optionalAddon = addonRepository.findByName(addonInput.getAddon().getName());

                    addonInput.setAddon(optionalAddon.orElse(addonInput.getAddon()));
                });
            }
            else if (pizza instanceof StandardPizza)
            {
                Optional<StandardPizza> optionalStandardPizza = standardPizzaRepository.findByName(((StandardPizza) pizza).getName());

                optionalStandardPizza.ifPresent(standardPizza -> {
                    int index = orderPizzaCart.getPizzas().indexOf(pizza);
                    orderPizzaCart.getPizzas().set(index, standardPizza);
                });
            }
        });
        return orderPizzaCartRepository.save(orderPizzaCart);
    }
}
