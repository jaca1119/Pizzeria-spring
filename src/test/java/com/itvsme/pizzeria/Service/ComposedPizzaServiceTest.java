package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.ComposedPizza;
import com.itvsme.pizzeria.Model.OrderPizza;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.ComposedPizzaRepository;
import com.itvsme.pizzeria.Repository.OrderPizzaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ComposedPizzaServiceTest
{
    @Autowired
    private ComposedPizzaRepository repository;

    @Autowired
    private OrderPizzaRepository orderPizzaRepository;

    @AfterEach
    void tearDown()
    {
        repository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        repository.deleteAll();
    }

    @Test
    void saveComposedPizzaTest()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(repository, orderPizzaRepository);
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        ComposedPizza composedPizza = new ComposedPizza(Stream.of(onion, pepper).collect(Collectors.toList()));

        ComposedPizza saved = composedPizzaService.saveComposedPizza(composedPizza);

        assertEquals(1, repository.count());
        assertEquals(composedPizza, saved);
    }

    @Test
    void findAllComposedPizzas()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(repository, orderPizzaRepository);
        List<ComposedPizza> composedPizzas = new ArrayList<>();
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        ComposedPizza sampleComposed = new ComposedPizza(Stream.of(onion, pepper).collect(Collectors.toList()));

        Addon mice = new Addon("mice", 3L);
        Addon sample = new Addon("sample", 3L);
        ComposedPizza sampleComposedPizza = new ComposedPizza(Stream.of(mice, sample).collect(Collectors.toList()));

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
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(repository, orderPizzaRepository);
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        ComposedPizza sampleComposed = new ComposedPizza(Stream.of(onion, pepper).collect(Collectors.toList()));

        OrderPizza orderPizza = new OrderPizza("Customer name", "Customer surname", "phonenumber", sampleComposed);

        OrderPizza saved = composedPizzaService.saveOrder(orderPizza);

        assertEquals(orderPizza.getCustomerName(), saved.getCustomerName());
        assertEquals(orderPizza.getCustomerSurname(), saved.getCustomerSurname());
        assertEquals(orderPizza.getPhoneNumber(), saved.getPhoneNumber());
        assertEquals(orderPizza.getOrderedPizza(), saved.getOrderedPizza());
    }

    @Test
    void saveStandardPizzaOrderTest()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(repository, orderPizzaRepository);
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        StandardPizza sampleComposed = new StandardPizza("sample", Stream.of(onion, pepper).collect(Collectors.toList()));

        OrderPizza orderPizza = new OrderPizza("Customer name", "Customer surname", "phonenumber", sampleComposed);

        OrderPizza saved = composedPizzaService.saveOrder(orderPizza);

        assertEquals(orderPizza.getCustomerName(), saved.getCustomerName());
        assertEquals(orderPizza.getCustomerSurname(), saved.getCustomerSurname());
        assertEquals(orderPizza.getPhoneNumber(), saved.getPhoneNumber());
        assertEquals(orderPizza.getOrderedPizza(), saved.getOrderedPizza());
    }

    @Test
    void findAllOrderPizzaTest()
    {
        ComposedPizzaService composedPizzaService = new ComposedPizzaService(repository, orderPizzaRepository);
        Addon onion = new Addon("onion", 3L);
        Addon pepper = new Addon("pepper", 3L);
        Addon ham = new Addon("ham", 3L);
        StandardPizza sampleComposed = new StandardPizza("sample", Stream.of(onion, pepper).collect(Collectors.toList()));
        ComposedPizza secondComposed = new ComposedPizza(Stream.of(onion, pepper, ham).collect(Collectors.toList()));

        OrderPizza orderPizza = new OrderPizza("Customer name", "Customer surname", "phonenumber", sampleComposed);
        OrderPizza sampleOrder = new OrderPizza("Customer name sample", "Customer surname sample", "sample", secondComposed);

        composedPizzaService.saveOrder(orderPizza);
        composedPizzaService.saveOrder(sampleOrder);

        List<OrderPizza> allOrderPizza = composedPizzaService.findAllOrderPizza();

        assertEquals(orderPizzaRepository.count(), 2);
        assertEquals(orderPizza.getOrderedPizza(), allOrderPizza.get(0).getOrderedPizza());
        assertEquals(sampleOrder.getOrderedPizza(), allOrderPizza.get(1).getOrderedPizza());
    }
}
