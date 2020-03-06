package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.order.OrderPizzaCart;
import com.itvsme.pizzeria.repository.*;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
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
    @Autowired
    private SizeRepository sizeRepository;

    private OrderPizzaService orderPizzaService;


    @BeforeEach
    void setUp()
    {
        orderPizzaService = new OrderPizzaService(orderPizzaRepository, standardPizzaRepository, addonRepository, orderPizzaCartRepository, sizeRepository);
    }

    @AfterEach
    void tearDown()
    {
        addonRepository.deleteAll();
        standardPizzaRepository.deleteAll();
        orderPizzaRepository.deleteAll();
    }

    @Test
    void saveOrderPizzaCart()
    {
        OrderPizzaCart orderPizzaCart = givenOrderPizzaCart();

        OrderPizzaCart orderWithListSave = orderPizzaService.saveOrderPizzaCart(orderPizzaCart);

        assertThat(orderPizzaCart).isEqualTo(orderWithListSave);
    }

    @Test
    void saveOrderPizzaCartWithOrderedStandardPizza()
    {
        OrderPizzaCart orderPizzaCart = givenOrderPizzaCartOrderedStandardPizza();

        OrderPizzaCart savedOrder = orderPizzaService.saveOrderPizzaCart(orderPizzaCart);

        assertThat(orderPizzaCart.getPizzas()).containsAll(savedOrder.getPizzas());
    }
}
