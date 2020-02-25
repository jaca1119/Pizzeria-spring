package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Repository.AddonRepository;
import com.itvsme.pizzeria.Repository.OrderPizzaRepository;
import com.itvsme.pizzeria.Repository.OrderPizzaCartRepository;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderPizzaServiceTest
{
    @Autowired
    private OrderPizzaRepository orderPizzaRepository;
    @Autowired
    private StandardPizzaRepository standardPizzaRepository;
    @Autowired
    private AddonRepository addonRepository;
    @Autowired
    private OrderPizzaCartRepository orderPizzaCartRepository;

    private Addon onion;
    private Addon pepper;
    private Addon mice;
    private AddonInput onionInput;
    private AddonInput pepperInput;
    private AddonInput miceInput;

    private OrderPizzaService orderPizzaService;

    @BeforeEach
    void setUp()
    {
        orderPizzaService = new OrderPizzaService(orderPizzaRepository, standardPizzaRepository, addonRepository, orderPizzaCartRepository);
        onion = new Addon("onion", 3L);
        pepper = new Addon("pepper", 3L);
        mice = new Addon("mice", 3L);
        onionInput = new AddonInput(onion,2);
        pepperInput = new AddonInput(pepper, 1);
        miceInput = new AddonInput(mice, 2);
    }

    @AfterEach
    void tearDown()
    {
        addonRepository.deleteAll();
        standardPizzaRepository.deleteAll();
        orderPizzaRepository.deleteAll();
    }

    @Test
    void saveStandardPizzaToOrderPizza()
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        OrderPizza saved = orderPizzaService.saveOrder(orderStandardPizza);

        assertThat(orderStandardPizza).isEqualTo(saved);
    }

    @Test
    void saveStandardPizzaAndComposedPizzaToOrderPizza()
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        orderPizzaService.saveOrder(orderStandardPizza);
        orderPizzaService.saveOrder(orderComposedPizza);

        List<OrderPizza> orderPizzas =  orderPizzaService.findAll();

        assertThat(Lists.list(orderStandardPizza, orderComposedPizza)).isEqualTo(orderPizzas);
        assertThat(addonRepository.count()).isEqualTo(3);
    }

    @Test
    void saveAllOrdersPizza()
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        List<OrderPizza> orderPizzaList = Lists.list(orderStandardPizza, orderComposedPizza);

        List<OrderPizza> orderPizzas = orderPizzaService.saveAll(orderPizzaList);

        assertThat(orderPizzaList).isEqualTo(orderPizzas);
    }

    @Test
    void saveOrderPizzaCart()
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        List<OrderPizza> orderPizzaList = Lists.list(orderStandardPizza, orderComposedPizza);

        OrderPizzaCart orderPizzaCart = new OrderPizzaCart("name", "surname", "phone", orderPizzaList);

        OrderPizzaCart orderWithListSave = orderPizzaService.saveOrderPizzaCart(orderPizzaCart);

        assertThat(orderPizzaCart).isEqualTo(orderWithListSave);
    }
}
