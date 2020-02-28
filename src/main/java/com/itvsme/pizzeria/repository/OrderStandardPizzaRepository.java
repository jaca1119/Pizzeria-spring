package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.OrderStandardPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStandardPizzaRepository extends CrudRepository<OrderStandardPizza, Integer>
{
}
