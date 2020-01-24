package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.ComposedPizzaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComposedPizzaService
{
    private ComposedPizzaRepository repository;

    public ComposedPizzaService(ComposedPizzaRepository repository)
    {
        this.repository = repository;
    }

    public ComposedPizza save(ComposedPizza composedPizza)
    {
        return repository.save(composedPizza);
    }

    public List<ComposedPizza> findAll()
    {
        return repository.findAll();
    }
}
