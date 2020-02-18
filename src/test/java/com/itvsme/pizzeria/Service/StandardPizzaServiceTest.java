package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.OrderPizza;
import com.itvsme.pizzeria.Model.OrderStandardPizza;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.AddonRepository;
import com.itvsme.pizzeria.Repository.OrderStandardPizzaRepository;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StandardPizzaServiceTest
{
    @Autowired
    private StandardPizzaRepository repository;
    @Autowired
    private AddonRepository addonRepository;
    @Autowired
    private OrderStandardPizzaRepository orderStandardPizzaRepository;

    private StandardPizzaService standardPizzaService;


    private Addon onion;
    private Addon pepper;
    private Addon mice;
    private Addon sample;

    @AfterEach
    void tearDown()
    {
        repository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        standardPizzaService = new StandardPizzaService(repository, addonRepository);
        onion = new Addon("onion", 3L);
        pepper = new Addon("pepper", 3L);
        mice = new Addon("mice", 3L);
        sample = new Addon("sample", 3L);
    }

    @Test
    void findLastStandardPizza()
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        repository.save(margherita);

        List<StandardPizza> standardPizzas = standardPizzaService.findAll();
        StandardPizza lastStandardPizza = standardPizzas.get(standardPizzas.size() - 1);

        assertEquals("Margherita", lastStandardPizza.getName());
    }

    @Test
    void saveStandardPizzaTest()
    {

        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        StandardPizza saved = standardPizzaService.save(margherita);

        assertEquals(1, repository.count());
        assertEquals(margherita, saved);
    }

    @Test
    @Transactional
    void findAllStandardPizzas()
    {
        List<StandardPizza> standardPizzaList = new ArrayList<>();

        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));


        StandardPizza sampleStandardPizza = new StandardPizza("Sample", Stream.of(mice, sample).collect(Collectors.toList()));

        repository.save(margherita);
        repository.save(sampleStandardPizza);

        standardPizzaList.add(margherita);
        standardPizzaList.add(sampleStandardPizza);

        List<StandardPizza> standardPizzas = standardPizzaService.findAll();

        assertArrayEquals(standardPizzaList.toArray(), standardPizzas.toArray());
    }

    @Test
    void deleteStandardPizzaById()
    {
        List<StandardPizza> standardPizzaList = new ArrayList<>();

        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        repository.save(margherita);


        standardPizzaService.deleteById(margherita.getId());
        System.out.println(margherita.getId());

        assertEquals(repository.count(), 0);
    }

    @Test
    void saveOrderStandardPizzaWithStandardPizzaInRepo()
    {
        StandardPizzaService standardPizzaService = new StandardPizzaService(repository, addonRepository, orderStandardPizzaRepository);
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        standardPizzaService.save(new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toList())));
        OrderStandardPizza saveOrderStandardPizza = standardPizzaService.saveOrderStandardPizza(orderStandardPizza);

        assertEquals(orderStandardPizza, saveOrderStandardPizza);
        assertEquals(repository.count(), 1);
        assertEquals(addonRepository.count(), 2);
    }
}