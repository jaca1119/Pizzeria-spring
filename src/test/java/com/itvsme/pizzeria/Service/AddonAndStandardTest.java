package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private OrderPizzaRepository orderPizzaRepository;
    @Autowired
    private OrderComposedPizzaRepository orderComposedPizzaRepository;
    @Autowired
    private OrderStandardPizzaRepository orderStandardPizzaRepository;

    private Addon onion;
    private Addon pepper;
    private Addon mice;
    private AddonInput onionInput;
    private AddonInput pepperInput;
    private AddonInput miceInput;


    @AfterEach
    void tearDown()
    {
        standardPizzaRepository.deleteAll();
        composedPizzaRepository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        onion = new Addon("onion", 3L);
        pepper = new Addon("pepper", 3L);
        mice = new Addon("mice", 3L);
        onionInput = new AddonInput(onion,2);
        pepperInput = new AddonInput(pepper, 1);
        miceInput = new AddonInput(mice, 2);
    }

    @Test
    void saveStandardPizzaWithoutCreatingNewAddonTest()
    {
        StandardPizzaService standardPizzaService = new StandardPizzaService(standardPizzaRepository, addonRepository);

        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        standardPizzaService.save(margherita);

        Addon secondOnion = new Addon("onion", 3L);
        StandardPizza second = new StandardPizza("Second", Stream.of(secondOnion).collect(Collectors.toSet()));

        standardPizzaService.save(second);

        assertThat(addonRepository.count()).isEqualTo(2);
    }

    @Test
    void saveComposedPizzaWithoutCreatingNewAddonTest()
    {
        ComposedPizzaService standardPizzaService = new ComposedPizzaService(composedPizzaRepository, addonRepository);


        ComposedPizza margherita = new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet()));

        standardPizzaService.saveComposedPizza(margherita);

        AddonInput secondOnion = new AddonInput(new Addon("onion", 3), 2);
        ComposedPizza second = new ComposedPizza(Stream.of(secondOnion).collect(Collectors.toSet()));

        standardPizzaService.saveComposedPizza(second);

        assertThat(addonRepository.count()).isEqualTo(2);
    }

    @Test
    void getOrderPizza()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);
        StandardPizzaService standardPizzaService = new StandardPizzaService(standardPizzaRepository, addonRepository, orderStandardPizzaRepository);


        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));
        StandardPizza samplePizza = new StandardPizza("Sample", Stream.of(onion).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name", "surname", "phone", margherita);
        OrderStandardPizza sample = new OrderStandardPizza("sample", "sample", "123", samplePizza);

        standardPizzaService.saveOrderStandardPizza(orderStandardPizza);
        standardPizzaService.saveOrderStandardPizza(sample);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        composedPizzaService.saveOrder(orderComposedPizza);
        System.out.println(orderPizzaRepository.findAll());
        assertThat(orderPizzaRepository.findAll().spliterator().estimateSize()).isEqualTo(3);
    }
}
