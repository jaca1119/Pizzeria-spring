package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.AddonRepository;
import com.itvsme.pizzeria.Repository.ComposedPizzaRepository;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    @AfterEach
    void tearDown()
    {
        standardPizzaRepository.deleteAll();
        composedPizzaRepository.deleteAll();
    }

    @Test
    void saveStandardPizzaWithoutCreatingNewAddonTest()
    {
        StandardPizzaService standardPizzaService = new StandardPizzaService(standardPizzaRepository, addonRepository);

        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        standardPizzaService.save(margherita);

        Addon secondOnion = new Addon("onion", 3L);
        StandardPizza second = new StandardPizza("Second", Stream.of(secondOnion).collect(Collectors.toList()));

        standardPizzaService.save(second);

        assertThat(addonRepository.count()).isEqualTo(2);
    }

    @Test
    void saveComposedPizzaWithoutCreatingNewAddonTest()
    {
        ComposedPizzaService standardPizzaService = new ComposedPizzaService(composedPizzaRepository, addonRepository);

        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        ComposedPizza margherita = new ComposedPizza(Stream.of(onion, pepper).collect(Collectors.toList()));

        standardPizzaService.saveComposedPizza(margherita);

        Addon secondOnion = new Addon("onion", 3L);
        ComposedPizza second = new ComposedPizza(Stream.of(secondOnion).collect(Collectors.toList()));

        standardPizzaService.saveComposedPizza(second);

        assertThat(addonRepository.count()).isEqualTo(2);
    }
}
