package com.itvsme.pizzeria.Service;

import com.itvsme.pizzeria.Repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PizzaServiceTest
{
    @Autowired
    private PizzaRepository repository;

    @Test
    void findAllStandardPizzas()
    {
    }
}