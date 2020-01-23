package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.Pizza;
import com.itvsme.pizzeria.PizzeriaApplication;
import com.itvsme.pizzeria.Repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaServiceTest
{
    @Autowired
    private PizzaRepository repository;

    @Test
    void findAllStandardPizzas()
    {
        Addon[] margheritaAddons = {new Addon("onion", 3L), new Addon("pepper", 3L)};
        Pizza margherita = new Pizza("Margherita", Arrays.asList(margheritaAddons));

        repository.save(margherita);
        PizzaService pizzaService = new PizzaService(repository);

        List<Pizza> pizzas = pizzaService.findAll();
        Pizza lastPizza = pizzas.get(pizzas.size() - 1);

        assertEquals("Margherita", lastPizza.getName());

    }

    @Test
    void savePizzaTest()
    {
        PizzaService pizzaService = new PizzaService(repository);

        List<Pizza> pizzaList = new ArrayList<>();
        Addon[] margheritaAddons = {new Addon("onion", 3L), new Addon("pepper", 3L)};
        Pizza margherita = new Pizza("Margherita", Arrays.asList(margheritaAddons));

        pizzaService.save(margherita);

        assertEquals(1, repository.count());

    }
}