package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.StandardPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardPizzaRepository extends CrudRepository<StandardPizza, Integer>
{
    Optional<StandardPizza> findByName(String name);
}
