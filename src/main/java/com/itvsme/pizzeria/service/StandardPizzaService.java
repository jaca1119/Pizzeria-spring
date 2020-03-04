package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.Addon;
import com.itvsme.pizzeria.model.StandardPizza;
import com.itvsme.pizzeria.repository.AddonRepository;
import com.itvsme.pizzeria.repository.StandardPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StandardPizzaService
{
    private StandardPizzaRepository standardPizzaRepository;

    private AddonRepository addonRepository;

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

    public Void deleteById(int id)
    {
        standardPizzaRepository.deleteById(id);
        return null;
    }
}
