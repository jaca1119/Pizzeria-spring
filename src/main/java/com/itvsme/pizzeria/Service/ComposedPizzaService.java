package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.AddonInput;
import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Model.OrderComposedPizza;
import com.itvsme.pizzeria.Repository.AddonRepository;
import com.itvsme.pizzeria.Repository.ComposedPizzaRepository;
import com.itvsme.pizzeria.Repository.OrderComposedPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ComposedPizzaService
{
    private ComposedPizzaRepository repository;
    private OrderComposedPizzaRepository orderComposedPizzaRepository;
    private AddonRepository addonRepository;

    @Autowired
    public ComposedPizzaService(ComposedPizzaRepository repository, OrderComposedPizzaRepository orderComposedPizzaRepository, AddonRepository addonRepository)
    {
        this.repository = repository;
        this.orderComposedPizzaRepository = orderComposedPizzaRepository;
        this.addonRepository = addonRepository;
    }


    public ComposedPizzaService(ComposedPizzaRepository repository, AddonRepository addonRepository)
    {
        this.repository = repository;
        this.addonRepository = addonRepository;
    }

    public ComposedPizzaService(ComposedPizzaRepository repository, OrderComposedPizzaRepository orderComposedPizzaRepository)
    {
        this.repository = repository;
        this.orderComposedPizzaRepository = orderComposedPizzaRepository;
    }

    public ComposedPizza saveComposedPizza(ComposedPizza composedPizza)
    {
        List<Addon> addonsFromInput = composedPizza.getAddons();
        List<Addon> addonsOutput = new ArrayList<>();

        addonsFromInput.forEach(addon -> {
            Optional<Addon> addonByName = addonRepository.findByName(addon.getName());

            addonsOutput.add(addonByName.orElse(addon));
        });

        composedPizza.setAddons(addonsOutput);

        return repository.save(composedPizza);
    }

    public List<ComposedPizza> findAllComposedPizza()
    {
        List<ComposedPizza> composed = new ArrayList<>();
        repository.findAll().forEach(composed::add);
        return composed;
    }

    public OrderComposedPizza saveOrder(OrderComposedPizza orderComposedPizza)
    {
        Set<AddonInput> addonsFromInput = orderComposedPizza.getAddonInputs();

        addonsFromInput.forEach(addon -> {
            Optional<Addon> addonByName = addonRepository.findByName(addon.getAddon().getName());

            addon.setAddon(addonByName.orElse(addon.getAddon()));
        });

        return orderComposedPizzaRepository.save(orderComposedPizza);
    }

    public List<OrderComposedPizza> findAllOrderPizza()
    {
        List<OrderComposedPizza> orderComposedPizzas = new ArrayList<>();
        orderComposedPizzaRepository.findAll().forEach(orderComposedPizzas::add);
        return orderComposedPizzas;
    }
}
