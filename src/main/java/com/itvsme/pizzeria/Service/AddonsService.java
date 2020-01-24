package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Repository.AddonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddonsService
{
    private AddonRepository addonRepository;

    public AddonsService(AddonRepository addonRepository)
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

}
