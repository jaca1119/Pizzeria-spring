package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.StandardPizza;
import com.itvsme.pizzeria.repository.AddonRepository;
import com.itvsme.pizzeria.repository.StandardPizzaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StandardPizzaServiceTest
{
    @Autowired
    private StandardPizzaRepository standardPizzaRepository;
    @Autowired
    private AddonRepository addonRepository;

    private StandardPizzaService standardPizzaService;

    @AfterEach
    void tearDown()
    {
        standardPizzaRepository.deleteAll();
        addonRepository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        standardPizzaService = new StandardPizzaService(standardPizzaRepository, addonRepository);
    }

    @Test
    void findLastStandardPizza()
    {
        StandardPizza margherita = givenStandardPizzaMargherita();

        standardPizzaService.save(margherita);

        List<StandardPizza> standardPizzas = standardPizzaService.findAll();
        StandardPizza lastStandardPizza = standardPizzas.get(standardPizzas.size() - 1);

        assertEquals("Margherita", lastStandardPizza.getName());
    }

    @Test
    void saveStandardPizzaTest()
    {
        StandardPizza margherita = givenStandardPizzaMargherita();
        StandardPizza sample = givenStandardPizzaSample();

        StandardPizza savedMargherita = standardPizzaService.save(margherita);
        StandardPizza savedSample = standardPizzaService.save(sample);

        assertEquals(2, standardPizzaRepository.count());
        assertThat(margherita.getAddons().containsAll(savedMargherita.getAddons())).isTrue();
        assertThat(sample.getAddons().containsAll(savedSample.getAddons())).isTrue();
    }

    @Test
    @Transactional
    void findAllStandardPizzas()
    {
        List<StandardPizza> standardPizzaList = new ArrayList<>();

        StandardPizza margherita = givenStandardPizzaMargherita();
        StandardPizza sampleStandardPizza = givenStandardPizzaSample();

        standardPizzaList.add(margherita);
        standardPizzaList.add(sampleStandardPizza);

        standardPizzaService.save(margherita);
        standardPizzaService.save(sampleStandardPizza);

        List<StandardPizza> standardPizzas = standardPizzaService.findAll();

        assertArrayEquals(standardPizzaList.toArray(), standardPizzas.toArray());
    }

    @Test
    void deleteStandardPizzaById()
    {
        StandardPizza margherita = givenStandardPizzaMargherita();

        standardPizzaService.save(margherita);

        standardPizzaService.deleteById(margherita.getId());

        assertEquals(standardPizzaRepository.count(), 0);
    }
}