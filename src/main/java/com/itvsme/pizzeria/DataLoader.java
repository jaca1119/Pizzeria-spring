package com.itvsme.pizzeria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner
{
    @Autowired
    private AddonRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        repository.save(new Addon("onion", 3L));
        repository.save(new Addon("pepper", 2L));

    }
}
