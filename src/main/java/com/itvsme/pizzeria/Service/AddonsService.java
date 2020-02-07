package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Repository.AddonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Void deleteById(int id)
    {
        addonRepository.deleteById(id);
        return null;
    }

    public Addon findById(int i)
    {
        Optional<Addon> addon = addonRepository.findById(i);

        return addon.orElse(null);
    }
}
