package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.addon.Addon;
import com.itvsme.pizzeria.model.addon.AddonInput;
import com.itvsme.pizzeria.model.pizza.ComposedPizza;
import com.itvsme.pizzeria.repository.AddonRepository;
import com.itvsme.pizzeria.repository.ComposedPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ComposedPizzaService
{
    private ComposedPizzaRepository repository;
    private AddonRepository addonRepository;

    public ComposedPizzaService(ComposedPizzaRepository repository, AddonRepository addonRepository)
    {
        this.repository = repository;
        this.addonRepository = addonRepository;
    }

    public ComposedPizza saveComposedPizza(ComposedPizza composedPizza)
    {
        Set<AddonInput> addonsInput = composedPizza.getAddonsInput();

        addonsInput.forEach(addonInput -> {
            Optional<Addon> optionalAddon = addonRepository.findByName(addonInput.getAddon().getName());

            addonInput.setAddon(optionalAddon.orElse(addonInput.getAddon()));
        });

        return repository.save(composedPizza);
    }

    public List<ComposedPizza> findAllComposedPizza()
    {
        List<ComposedPizza> composed = new ArrayList<>();
        repository.findAll().forEach(composed::add);
        return composed;
    }
}
