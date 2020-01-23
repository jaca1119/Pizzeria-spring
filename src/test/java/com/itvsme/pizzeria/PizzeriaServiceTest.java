package com.itvsme.pizzeria;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzeriaServiceTest
{
    @Autowired
    private AddonRepository addonRepository;

    @AfterEach
    void tearDown()
    {
        addonRepository.deleteAll();
    }

    @Test
    void getAllAddons()
    {
        Addon addon = new Addon("cucumber", 2L);
        addonRepository.save(addon);
        PizzeriaService pizzeriaService = new PizzeriaService(addonRepository);

        List<Addon> addons = pizzeriaService.findAll();
        Addon firstAddon = addons.get(addons.size() - 1);

        assertEquals(addon.getName(), firstAddon.getName());
        assertEquals(addon.getPrice(), firstAddon.getPrice());
        assertEquals(addon.getId(), firstAddon.getId());
    }

    @Test
    void saveAddon()
    {
        PizzeriaService service = new PizzeriaService(addonRepository);
        Addon sample = new Addon("sample", 1L);

        service.save(sample);

        assertEquals(1.0, addonRepository.count());
    }
}