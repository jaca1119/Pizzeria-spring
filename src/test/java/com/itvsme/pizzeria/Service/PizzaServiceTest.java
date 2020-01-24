package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.Pizza;
import com.itvsme.pizzeria.PizzeriaApplication;
import com.itvsme.pizzeria.Repository.PizzaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaServiceTest
{
    @Autowired
    private PizzaRepository repository;

    @AfterEach
    void tearDown()
    {
        repository.deleteAll();
    }

    @Test
    void findLastStandardPizza()
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

        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        Pizza margherita = new Pizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        Pizza saved = pizzaService.save(margherita);

        assertEquals(1, repository.count());
        assertEquals(margherita, saved);
    }

    @Test
    @Transactional
    void findAllStandardPizzas()
    {
        List<Pizza> pizzaList = new ArrayList<>();
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        Pizza margherita = new Pizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        Addon mice = new Addon("mice", 3L);
        Addon sample = new Addon("sample", 3L);
        Pizza samplePizza = new Pizza("Sample", Stream.of(mice, sample).collect(Collectors.toList()));

        repository.save(margherita);
        repository.save(samplePizza);

        pizzaList.add(margherita);
        pizzaList.add(samplePizza);

        PizzaService pizzaService = new PizzaService(repository);

        List<Pizza> pizzas = pizzaService.findAll();

        assertArrayEquals(pizzaList.toArray(), pizzas.toArray());
    }
}