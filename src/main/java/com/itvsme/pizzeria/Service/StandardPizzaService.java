package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardPizzaService
{
    private StandardPizzaRepository standardPizzaRepository;

    public StandardPizzaService(StandardPizzaRepository standardPizzaRepository)
    {
        this.standardPizzaRepository = standardPizzaRepository;
    }

    public List<StandardPizza> findAll()
    {
        return standardPizzaRepository.findAll();
    }

    public StandardPizza save(StandardPizza standardPizza)
    {
        return standardPizzaRepository.save(standardPizza);
    }

}
