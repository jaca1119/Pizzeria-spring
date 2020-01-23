package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Pizza;
import com.itvsme.pizzeria.Repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService
{
    private PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository)
    {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> findAll()
    {
        return pizzaRepository.findAll();
    }

    public Pizza save(Pizza pizza)
    {
        return pizzaRepository.save(pizza);
    }

}
