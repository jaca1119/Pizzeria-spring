package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.StandardPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardPizzaRepository extends CrudRepository<StandardPizza, Integer>
{
    Optional<StandardPizza> findByName(String name);
}
