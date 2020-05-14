package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.order.OrderPizzaCart;
import com.itvsme.pizzeria.model.payment.Payment;
import com.itvsme.pizzeria.repository.*;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

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
        orderPizzaCartRepository.deleteAll();
        orderPizzaRepository.deleteAll();
    }

    @Test
    void saveOrderPizzaCart()
    {
        OrderPizzaCart orderPizzaCart = givenOrderPizzaCart();

        OrderPizzaCart savedOrder = orderPizzaService.saveOrderPizzaCart(orderPizzaCart);

        assertThat(orderPizzaCart).isEqualTo(savedOrder);
        assertThat(savedOrder.getPaymentStatus()).isEqualByComparingTo(Payment.PENDING);
    }

    @Test
    void saveOrderPizzaCartWithOrderedStandardPizza()
    {
        OrderPizzaCart orderPizzaCart = givenOrderPizzaCartOrderedStandardPizza();

        OrderPizzaCart savedOrder = orderPizzaService.saveOrderPizzaCart(orderPizzaCart);

        assertThat(orderPizzaCart.getPizzas()).containsAll(savedOrder.getPizzas());
    }

    @Test
    void acceptPaymentForOrder()
    {
        int id = 1;
        orderPizzaCartRepository.save(givenOrderPizzaCart());

        boolean isAcceptSuccessful = orderPizzaService.acceptPaymentForOrder(id);
        Optional<OrderPizzaCart> orderedOptional = orderPizzaCartRepository.findById(1);

        if (orderedOptional.isPresent())
        {
            assertThat(isAcceptSuccessful).isTrue();
            assertThat(orderedOptional.get().getPaymentStatus()).isEqualByComparingTo(Payment.ACCEPTED);

            assertThat(orderPizzaCartRepository.count()).isEqualTo(1);
            assertThat(addonRepository.count()).isEqualTo(4);
        }
        else
        {
            fail();
        }
    }
}
