package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.*;
import com.itvsme.pizzeria.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ComposedPizzaServiceTest
{
    @Autowired
    private ComposedPizzaRepository composedPizzaRepository;
    @Autowired
    private AddonRepository addonRepository;
    @Autowired
    private AddonInputRepository addonInputRepository;

    private ComposedPizzaService composedPizzaService;

    @AfterEach
    void tearDown()
    {
        composedPizzaRepository.deleteAll();
        addonRepository.deleteAll();
        addonInputRepository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        composedPizzaService = new ComposedPizzaService(composedPizzaRepository, addonRepository);
    }

    @Test
    void saveComposedPizzaTest()
    {
        ComposedPizza composedPizza = givenComposedPizza();

        ComposedPizza saved = composedPizzaService.saveComposedPizza(composedPizza);

        assertEquals(1, composedPizzaRepository.count());
        assertEquals(composedPizza.getAddonsInput(), saved.getAddonsInput());
    }

    @Test
    void findAllComposedPizzas()
    {
        List<ComposedPizza> composedPizzas = new ArrayList<>();

        ComposedPizza composedPizza = givenComposedPizza();

        ComposedPizza composedPizzaDifferent = givenComposedPizzaDifferent();

        composedPizzas.add(composedPizza);
        composedPizzas.add(composedPizzaDifferent);

        composedPizzaService.saveComposedPizza(composedPizza);
        composedPizzaService.saveComposedPizza(composedPizzaDifferent);

        List<ComposedPizza> composedPizzaList = composedPizzaService.findAllComposedPizza();

        assertArrayEquals(composedPizzas.toArray(), composedPizzaList.toArray());
    }
}
