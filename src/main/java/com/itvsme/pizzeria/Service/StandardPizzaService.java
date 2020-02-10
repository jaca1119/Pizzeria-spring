package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.AddonRepository;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Addon> addonsFromInput = standardPizza.getAddons();
        List<Addon> addonsOutput = new ArrayList<>();

        addonsFromInput.forEach(addon -> {
            Optional<Addon> addonByName = addonRepository.findByName(addon.getName());

            addonsOutput.add(addonByName.orElse(addon));
        });

        standardPizza.setAddons(addonsOutput);

        return standardPizzaRepository.save(standardPizza);
    }

}
