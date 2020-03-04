package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.OrderComposedPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderComposedPizzaRepository extends CrudRepository<OrderComposedPizza, Integer>
{
}
