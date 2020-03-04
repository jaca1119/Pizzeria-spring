package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.*;
import com.itvsme.pizzeria.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddonAndStandardTest
{
    @Autowired
    private AddonRepository addonRepository;
    @Autowired
    private StandardPizzaRepository standardPizzaRepository;
    @Autowired
    private ComposedPizzaRepository composedPizzaRepository;

    private StandardPizzaService standardPizzaService;
    private ComposedPizzaService composedPizzaService;



    @AfterEach
    void tearDown()
    {
        standardPizzaRepository.deleteAll();
        composedPizzaRepository.deleteAll();
        addonRepository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        standardPizzaService = new StandardPizzaService(standardPizzaRepository, addonRepository);
        composedPizzaService = new ComposedPizzaService(composedPizzaRepository, addonRepository);
        addonRepository.deleteAll();
    }

    @Test
    void saveStandardPizzaWithoutCreatingNewAddonTest()
    {
        StandardPizza margherita = givenStandardPizzaMargherita();
        StandardPizza sample = givenStandardPizzaSample();

        standardPizzaService.save(margherita);
        standardPizzaService.save(sample);

        assertThat(addonRepository.count()).isEqualTo(2);
    }

    @Test
    void saveComposedPizzaWithoutCreatingNewAddonTest()
    {
        ComposedPizza composedPizza = givenComposedPizza();
        ComposedPizza second = givenComposedPizzaDifferent();

        composedPizzaService.saveComposedPizza(composedPizza);
        composedPizzaService.saveComposedPizza(second);

        assertThat(addonRepository.count()).isEqualTo(3);
    }
}
