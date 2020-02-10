package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.StandardPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardPizzaRepository extends CrudRepository<StandardPizza, Integer>
{
}
