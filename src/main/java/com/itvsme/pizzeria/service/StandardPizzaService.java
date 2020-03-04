package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.Addon;
import com.itvsme.pizzeria.model.OrderStandardPizza;
import com.itvsme.pizzeria.model.StandardPizza;
import com.itvsme.pizzeria.repository.AddonRepository;
import com.itvsme.pizzeria.repository.OrderStandardPizzaRepository;
import com.itvsme.pizzeria.repository.StandardPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StandardPizzaService
{
    private StandardPizzaRepository standardPizzaRepository;

    private AddonRepository addonRepository;
    private OrderStandardPizzaRepository orderStandardPizzaRepository;

    @Autowired
    public StandardPizzaService(StandardPizzaRepository standardPizzaRepository, AddonRepository addonRepository, OrderStandardPizzaRepository orderStandardPizzaRepository)
    {
        this.standardPizzaRepository = standardPizzaRepository;
        this.addonRepository = addonRepository;
        this.orderStandardPizzaRepository = orderStandardPizzaRepository;
    }

    public StandardPizzaService(StandardPizzaRepository standardPizzaRepository, AddonRepository addonRepository)
    {
        this.standardPizzaRepository = standardPizzaRepository;
        this.addonRepository = addonRepository;
    }

    public List<StandardPizza> findAll()
    {
        List<StandardPizza> standard = new ArrayList<>();
        standardPizzaRepository.findAll().forEach(standard::add);
        return standard;
    }

    public StandardPizza save(StandardPizza standardPizza)
    {
        Set<Addon> addonsFromInput = standardPizza.getAddons();
        Set<Addon> addonsOutput = new HashSet<>();

        addonsFromInput.forEach(addon -> {
            Optional<Addon> addonByName = addonRepository.findByName(addon.getName());

            addonsOutput.add(addonByName.orElse(addon));
        });

        standardPizza.setAddons(addonsOutput);

        return standardPizzaRepository.save(standardPizza);
    }

    public void deleteById(int id)
    {
        standardPizzaRepository.deleteById(id);
    }

    public OrderStandardPizza saveOrderStandardPizza(OrderStandardPizza orderStandardPizza)
    {
        Optional<StandardPizza> optionalStandardPizza = standardPizzaRepository.findByName(orderStandardPizza.getStandardPizza().getName());

        if (optionalStandardPizza.isPresent())
        {
            orderStandardPizza.setStandardPizza(optionalStandardPizza.get());
        }
        else
        {
            Set<Addon> addons = orderStandardPizza.getStandardPizza().getAddons();
            Set<Addon> addonsWithExistingAddons = new HashSet<>();
            addons.forEach(addon -> {
                Optional<Addon> optionalAddon = addonRepository.findByName(addon.getName());

                addonsWithExistingAddons.add(optionalAddon.orElse(addon));
            });

            orderStandardPizza.getStandardPizza().setAddons(addonsWithExistingAddons);
        }

        return orderStandardPizzaRepository.save(orderStandardPizza);
    }

    public List<OrderStandardPizza> findAllOrdersStandard()
    {
        List<OrderStandardPizza> orderStandardPizzas = new ArrayList<>();
        orderStandardPizzaRepository.findAll().forEach(orderStandardPizzas::add);
        return orderStandardPizzas;
    }
}
