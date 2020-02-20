package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Repository.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ComposedPizzaServiceTest
{
    @Autowired
    private StandardPizzaRepository standardPizzaRepository;
    @Autowired
    private ComposedPizzaRepository composedPizzaRepository;
    @Autowired
    private OrderComposedPizzaRepository orderComposedPizzaRepository;
    @Autowired
    private AddonRepository addonRepository;
    @Autowired
    private OrderPizzaRepository orderPizzaRepository;
    @Autowired
    private AddonInputRepository addonInputRepository;

    private Addon onion;
    private Addon pepper;
    private Addon mice;
    private AddonInput onionInput;
    private AddonInput pepperInput;
    private AddonInput miceInput;

    @AfterEach
    void tearDown()
    {
        composedPizzaRepository.deleteAll();
        orderComposedPizzaRepository.deleteAll();
        addonRepository.deleteAll();
        addonInputRepository.deleteAll();
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
    @Transactional
    void saveComposedPizzaTest()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);

        ComposedPizza composedPizza = new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet()));

        ComposedPizza saved = composedPizzaService.saveComposedPizza(composedPizza);

        assertEquals(1, composedPizzaRepository.count());
        assertThat(saved.getAddonsInput()).containsExactlyInAnyOrder(onionInput, pepperInput);
    }

    @Test
    @Transactional
    void findAllComposedPizzas()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);

        List<ComposedPizza> composedPizzas = new ArrayList<>();


        ComposedPizza sampleComposed = new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet()));


        ComposedPizza sampleComposedPizza = new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet()));

        composedPizzaService.saveComposedPizza(sampleComposed);
        composedPizzaService.saveComposedPizza(sampleComposedPizza);

        composedPizzas.add(sampleComposed);
        composedPizzas.add(sampleComposedPizza);

        List<ComposedPizza> composedPizzaList = composedPizzaService.findAllComposedPizza();

        assertArrayEquals(composedPizzas.toArray(), composedPizzaList.toArray());
    }

    @Test
    void saveComposedPizzaOrderTest()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);

        ComposedPizza addonInputs = new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet()));

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                addonInputs);

        OrderComposedPizza saved = composedPizzaService.saveOrder(orderComposedPizza);

        assertEquals(orderComposedPizza.getName(), saved.getName());
        assertEquals(orderComposedPizza.getSurname(), saved.getSurname());
        assertEquals(orderComposedPizza.getPhone(), saved.getPhone());
        assertThat(orderComposedPizza.getComposedPizza().getAddonsInput()).containsExactlyInAnyOrder(saved.getComposedPizza().getAddonsInput().toArray(AddonInput[]::new));
        assertThat(orderComposedPizza.getTimestamp()).isNotNull();
    }


    @Test
    void saveOrderPizzaWithStandardPizza()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);

        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper, mice).collect(Collectors.toSet()));

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("name",
                "surname",
                "phone",
                new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet())));

        StandardPizza savedStandard = standardPizzaRepository.save(margherita);

        addonRepository.findAll().spliterator().forEachRemaining(System.out::println);
        OrderComposedPizza saved = composedPizzaService.saveOrder(orderComposedPizza);

        assertThat(addonRepository.count()).isEqualTo(3);
        assertThat(addonInputRepository.count()).isEqualTo(2);
    }

    @Test
    @Transactional
    void findAllOrderPizzaTest()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);

        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        Addon ham = new Addon("ham", 3L);

        AddonInput onionInput = new AddonInput(onion, 1);
        AddonInput pepperInput = new AddonInput(pepper, 2);
        AddonInput hamInput = new AddonInput(ham, 2);


        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet())));

        OrderComposedPizza sampleOrder = new OrderComposedPizza("Customer name sample",
                "Customer surname sample",
                "sample",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        composedPizzaService.saveOrder(orderComposedPizza);
        composedPizzaService.saveOrder(sampleOrder);

        List<OrderComposedPizza> allOrderComposedPizza = composedPizzaService.findAllOrderPizza();

        assertEquals(2, orderComposedPizzaRepository.count());
        assertThat(orderComposedPizza.getComposedPizza().getAddonsInput()).containsExactlyInAnyOrder(allOrderComposedPizza.get(0).getComposedPizza().getAddonsInput().toArray(AddonInput[]::new));
        assertThat(sampleOrder.getComposedPizza().getAddonsInput()).containsExactlyInAnyOrder(allOrderComposedPizza.get(1).getComposedPizza().getAddonsInput().toArray(AddonInput[]::new));
    }

    @Test
    void getOrderPizza()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);

        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);

        AddonInput onionInput = new AddonInput(onion, 1);
        AddonInput pepperInput = new AddonInput(pepper, 2);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        composedPizzaService.saveOrder(orderComposedPizza);

        assertThat(orderPizzaRepository.findAll().spliterator().estimateSize()).isEqualTo(1);
    }
}
