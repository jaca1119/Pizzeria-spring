package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StandardStandardPizzaServiceTest
{
    @Autowired
    private StandardPizzaRepository repository;

    @AfterEach
    void tearDown()
    {
        repository.deleteAll();
    }

    @Test
    void findLastStandardPizza()
    {
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        repository.save(margherita);
        StandardPizzaService standardPizzaService = new StandardPizzaService(repository);

        List<StandardPizza> standardPizzas = standardPizzaService.findAll();
        StandardPizza lastStandardPizza = standardPizzas.get(standardPizzas.size() - 1);

        assertEquals("Margherita", lastStandardPizza.getName());
    }

    @Test
    void saveStandardPizzaTest()
    {
        StandardPizzaService standardPizzaService = new StandardPizzaService(repository);

        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        StandardPizza saved = standardPizzaService.save(margherita);

        assertEquals(1, repository.count());
        assertEquals(margherita, saved);
    }

    @Test
    @Transactional
    void findAllStandardPizzas()
    {
        List<StandardPizza> standardPizzaList = new ArrayList<>();
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        Addon mice = new Addon("mice", 3L);
        Addon sample = new Addon("sample", 3L);
        StandardPizza sampleStandardPizza = new StandardPizza("Sample", Stream.of(mice, sample).collect(Collectors.toList()));

        repository.save(margherita);
        repository.save(sampleStandardPizza);

        standardPizzaList.add(margherita);
        standardPizzaList.add(sampleStandardPizza);

        StandardPizzaService standardPizzaService = new StandardPizzaService(repository);

        List<StandardPizza> standardPizzas = standardPizzaService.findAll();

        assertArrayEquals(standardPizzaList.toArray(), standardPizzas.toArray());
    }
}