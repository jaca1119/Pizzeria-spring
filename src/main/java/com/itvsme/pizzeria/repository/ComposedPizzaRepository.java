package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.ComposedPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposedPizzaRepository extends CrudRepository<ComposedPizza, Integer>
{
}
