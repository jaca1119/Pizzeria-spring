package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.ComposedPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposedPizzaRepository extends CrudRepository<ComposedPizza, Integer>
{
}
