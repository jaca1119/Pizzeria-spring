package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.addon.Addon;
import com.itvsme.pizzeria.model.addon.AddonInput;
import com.itvsme.pizzeria.model.order.OrderPizza;
import com.itvsme.pizzeria.model.order.OrderPizzaCart;
import com.itvsme.pizzeria.model.pizza.ComposedPizza;
import com.itvsme.pizzeria.model.pizza.OrderedStandardPizza;
import com.itvsme.pizzeria.model.pizza.Size;
import com.itvsme.pizzeria.model.pizza.StandardPizza;
import com.itvsme.pizzeria.repository.*;
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
    private SizeRepository sizeRepository;

    public OrderPizzaService(OrderPizzaRepository orderPizzaRepository, StandardPizzaRepository standardPizzaRepository, AddonRepository addonRepository, OrderPizzaCartRepository orderPizzaCartRepository, SizeRepository sizeRepository)
    {
        this.orderPizzaRepository = orderPizzaRepository;
        this.standardPizzaRepository = standardPizzaRepository;
        this.addonRepository = addonRepository;
        this.orderPizzaCartRepository = orderPizzaCartRepository;
        this.sizeRepository = sizeRepository;
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
                ComposedPizza composedPizza = (ComposedPizza) pizza;

                Set<AddonInput> addonsInput = composedPizza.getAddonsInput();
                addonsInput.forEach(addonInput -> {
                    Optional<Addon> optionalAddon = addonRepository.findByName(addonInput.getAddon().getName());

                    addonInput.setAddon(optionalAddon.orElse(addonInput.getAddon()));
                });

                Optional<Size> sizeInCm = sizeRepository.findBySizeInCm(composedPizza.getSize().getSizeInCm());
                sizeInCm.ifPresent(composedPizza::setSize);
            }
            else if (pizza instanceof StandardPizza)
            {
                Optional<StandardPizza> optionalStandardPizza = standardPizzaRepository.findByName(((StandardPizza) pizza).getName());

                optionalStandardPizza.ifPresent(standardPizza -> {
                    int index = orderPizzaCart.getPizzas().indexOf(pizza);
                    orderPizzaCart.getPizzas().set(index, standardPizza);
                });
            }
            else if (pizza instanceof OrderedStandardPizza)
            {
                OrderedStandardPizza orderedStandardPizza = (OrderedStandardPizza) pizza;

                Optional<StandardPizza> optionalStandardPizza = standardPizzaRepository.findByName(orderedStandardPizza.getStandardPizza().getName());

                optionalStandardPizza.ifPresent(orderedStandardPizza::setStandardPizza);

                Optional<Size> sizeInCm = sizeRepository.findBySizeInCm(orderedStandardPizza.getSize().getSizeInCm());
                sizeInCm.ifPresent(orderedStandardPizza::setSize);
            }
        });
        return orderPizzaCartRepository.save(orderPizzaCart);
    }
}
