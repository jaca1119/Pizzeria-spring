package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.OrderStandardPizza;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.AddonRepository;
import com.itvsme.pizzeria.Repository.OrderStandardPizzaRepository;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
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

        optionalStandardPizza.ifPresent(orderStandardPizza::setStandardPizza);

        return orderStandardPizzaRepository.save(orderStandardPizza);
    }

    public List<OrderStandardPizza> findAllOrdersStandard()
    {
        List<OrderStandardPizza> orderStandardPizzas = new ArrayList<>();
        orderStandardPizzaRepository.findAll().forEach(orderStandardPizzas::add);
        return orderStandardPizzas;
    }
}
