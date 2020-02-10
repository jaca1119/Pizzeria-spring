package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Repository.ComposedPizzaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ComposedPizzaServiceTest
{
    @Autowired
    private ComposedPizzaRepository repository;

    @AfterEach
    void tearDown()
    {
        repository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        repository.deleteAll();
    }

    @Test
    void saveComposedPizzaTest()
    {
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        ComposedPizza composedPizza = new ComposedPizza(Stream.of(onion, pepper).collect(Collectors.toList()));

        ComposedPizzaService composedPizzaService = new ComposedPizzaService(repository);

        ComposedPizza saved = composedPizzaService.save(composedPizza);

        assertEquals(1, repository.count());
        assertEquals(composedPizza, saved);
    }

    @Test
    @Transactional
    void findAllComposedPizzas()
    {
        List<ComposedPizza> composedPizzas = new ArrayList<>();
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        ComposedPizza sampleComposed = new ComposedPizza(Stream.of(onion, pepper).collect(Collectors.toList()));

        Addon mice = new Addon("mice", 3L);
        Addon sample = new Addon("sample", 3L);
        ComposedPizza sampleComposedPizza = new ComposedPizza(Stream.of(mice, sample).collect(Collectors.toList()));

        repository.save(sampleComposed);
        repository.save(sampleComposedPizza);

        composedPizzas.add(sampleComposed);
        composedPizzas.add(sampleComposedPizza);

        ComposedPizzaService composedPizzaService = new ComposedPizzaService(repository);

        List<ComposedPizza> composedPizzaList = composedPizzaService.findAll();

        assertArrayEquals(composedPizzas.toArray(), composedPizzaList.toArray());
    }
}
