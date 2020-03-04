package com.itvsme.pizzeria.service;

import com.itvsme.pizzeria.model.Addon;
import com.itvsme.pizzeria.repository.AddonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.givenAddon;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddonsServiceTest
{
    @Autowired
    private AddonRepository addonRepository;

    AddonsService service;


    @AfterEach
    void tearDown()
    {
        addonRepository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        service = new AddonsService(addonRepository);
        addonRepository.deleteAll();
    }

    @Test
    void getOneAddon()
    {
        Addon addon = new Addon("cucumber", 2L);

        Addon save = service.save(addon);

        assertEquals(addon, save);
    }

    @Test
    void deleteByIdAddon()
    {
        Addon sample = givenAddon();

        service.save(sample);

        service.deleteById(sample.getId());

        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void findByName()
    {
        Addon sample = givenAddon();

        service.save(sample);

        Optional<Addon> addon = service.findByName(sample.getName());

        assertEquals(sample.getName(), addon.orElseThrow().getName());
    }
}