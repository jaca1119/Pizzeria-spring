package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Repository.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
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

    private ComposedPizzaService composedPizzaService;

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
        composedPizzaService = new ComposedPizzaService(composedPizzaRepository, orderComposedPizzaRepository, addonRepository);
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

    @Test
    void saveComposedPizzaOrderTest()
    {
        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        OrderComposedPizza saved = composedPizzaService.saveOrder(orderComposedPizza);

        assertEquals(orderComposedPizza, saved);
    }

    @Test
    void saveOrderPizzaWithStandardPizza()
    {
        StandardPizza margherita = givenStandardPizzaMargherita();

        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        standardPizzaRepository.save(margherita);

        composedPizzaService.saveOrder(orderComposedPizza);

        assertThat(addonRepository.count()).isEqualTo(3);
        assertThat(addonInputRepository.count()).isEqualTo(2);
    }

    @Test
    void findAllOrderPizzaTest()
    {
        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        OrderComposedPizza sampleOrder = givenOrderComposedPizza();

        composedPizzaService.saveOrder(orderComposedPizza);
        composedPizzaService.saveOrder(sampleOrder);

        List<OrderComposedPizza> allOrderComposedPizza = composedPizzaService.findAllOrderPizza();

        assertEquals(2, orderComposedPizzaRepository.count());
        assertEquals(Lists.list(orderComposedPizza, sampleOrder), allOrderComposedPizza);
    }

    @Test
    void getOrderPizza()
    {
        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        OrderComposedPizza saveOrder = composedPizzaService.saveOrder(orderComposedPizza);

        assertEquals(orderComposedPizza, saveOrder);
    }
}
