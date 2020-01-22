package com.itvsme.pizzeria;

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

    @Test
    void getAllAddons()
    {
        Addon addon = new Addon("cucumber", 2L);
        addonRepository.save(addon);
        PizzeriaService pizzeriaService = new PizzeriaService(addonRepository);

        Addon firstAddon = pizzeriaService.findAll().get(0);

        assertEquals(addon.getName(), firstAddon.getName());
        assertEquals(addon.getPrice(), firstAddon.getPrice());
        assertEquals(addon.getId(), firstAddon.getId());
    }
}