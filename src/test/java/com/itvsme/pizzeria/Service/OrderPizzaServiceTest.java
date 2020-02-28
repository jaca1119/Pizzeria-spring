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
    void saveStandardPizzaToOrderPizza()
    {
        OrderStandardPizza orderStandardPizza = givenOrderStandardPizzaMargherita();

        OrderPizza saved = orderPizzaService.saveOrder(orderStandardPizza);

        assertThat(orderStandardPizza).isEqualTo(saved);
    }

    @Test
    void saveStandardPizzaAndComposedPizzaToOrderPizza()
    {
        OrderStandardPizza orderStandardPizza = givenOrderStandardPizzaMargherita();

        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        orderPizzaService.saveOrder(orderStandardPizza);
        orderPizzaService.saveOrder(orderComposedPizza);

        List<OrderPizza> orderPizzas =  orderPizzaService.findAll();

        assertThat(Lists.list(orderStandardPizza, orderComposedPizza)).isEqualTo(orderPizzas);
        assertThat(addonRepository.count()).isEqualTo(3);
    }

    @Test
    void saveAllOrdersPizza()
    {

        OrderStandardPizza orderStandardPizza = givenOrderStandardPizzaMargherita();

        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        List<OrderPizza> orderPizzaList = Lists.list(orderStandardPizza, orderComposedPizza);

        List<OrderPizza> orderPizzas = orderPizzaService.saveAll(orderPizzaList);

        assertThat(orderPizzaList).isEqualTo(orderPizzas);
    }

    @Test
    void saveOrderPizzaCart()
    {
        OrderPizzaCart orderPizzaCart = givenOrderPizzaCart();

        OrderPizzaCart orderWithListSave = orderPizzaService.saveOrderPizzaCart(orderPizzaCart);

        assertThat(orderPizzaCart).isEqualTo(orderWithListSave);
    }
}
