package com.itvsme.pizzeria;

import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.StandardPizza;
import com.itvsme.pizzeria.Repository.StandardPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataLoader implements ApplicationRunner
{
    private StandardPizzaRepository repository;

    @Autowired
    public DataLoader(StandardPizzaRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        Addon mozzarella = new Addon("mozzarella", 3L);
        Addon sauce = new Addon("tomato sauce", 3L);
        Addon basil = new Addon("basil", 2L);
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(mozzarella, sauce, basil).collect(Collectors.toList()));

        repository.save(margherita);
    }
}
