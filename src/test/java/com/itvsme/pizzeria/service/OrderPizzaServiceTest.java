package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.order.OrderPizzaCart;
import com.itvsme.pizzeria.repository.AddonRepository;
import com.itvsme.pizzeria.repository.OrderPizzaRepository;
import com.itvsme.pizzeria.repository.OrderPizzaCartRepository;
import com.itvsme.pizzeria.repository.StandardPizzaRepository;
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

    private OrderPizzaService orderPizzaService;

    @BeforeEach
    void setUp()
    {
        orderPizzaService = new OrderPizzaService(orderPizzaRepository, standardPizzaRepository, addonRepository, orderPizzaCartRepository);
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
}
