package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.Pizza;
import com.itvsme.pizzeria.Repository.AddonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzeriaService
{
    private AddonRepository addonRepository;

    public PizzeriaService(AddonRepository addonRepository)
    {
        this.addonRepository = addonRepository;
    }

    public List<Addon> findAll()
    {
        return addonRepository.findAll();
    }

    public Addon save(Addon addon)
    {
        return addonRepository.save(addon);
    }

    public List<Pizza> findAllStandardPizzas()
    {
        return new ArrayList<>();
    }
}
