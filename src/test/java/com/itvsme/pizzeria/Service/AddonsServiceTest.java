package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Repository.AddonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddonsServiceTest
{
    @Autowired
    private AddonRepository addonRepository;

    @AfterEach
    void tearDown()
    {
        addonRepository.deleteAll();
    }

    @BeforeEach
    void setUp()
    {
        addonRepository.deleteAll();
    }

    @Test
    void getOneAddon()
    {
        AddonsService service = new AddonsService(addonRepository);
        Addon addon = new Addon("cucumber", 2L);

        service.save(addon);

        Addon received = service.findById(addon.getId());

        assertEquals(addon.getId(), received.getId());
        assertEquals(addon.getName(), received.getName());
        assertEquals(addon.getPrice(), received.getPrice());
    }

    @Test
    void saveAndGetLastAddon()
    {
        Addon addon = new Addon("cucumber", 2L);
        addonRepository.save(addon);
        AddonsService addonsService = new AddonsService(addonRepository);

        List<Addon> addons = addonsService.findAll();
        Addon lastAddon = addons.get(addons.size() - 1);

        assertEquals(addon.getName(), lastAddon.getName());
        assertEquals(addon.getPrice(), lastAddon.getPrice());
        assertEquals(addon.getId(), lastAddon.getId());
    }

    @Test
    void saveAddon()
    {
        AddonsService service = new AddonsService(addonRepository);
        Addon sample = new Addon("sample", 1L);

        service.save(sample);

        assertEquals(1.0, addonRepository.count());
    }

    @Test
    void deleteByIdAddon()
    {
        AddonsService service = new AddonsService(addonRepository);

        Addon sample = new Addon("sample", 1L);

        service.save(sample);

        service.deleteById(sample.getId());

        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void findByName()
    {
        AddonsService service = new AddonsService(addonRepository);

        Addon sample = new Addon("sample", 1L);

        service.save(sample);

        Optional<Addon> addon = service.findByName("sample");

        assertEquals(sample.getName(), addon.orElseThrow().getName());
    }
}