package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.OrderPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPizzaRepository extends CrudRepository<OrderPizza, Integer>
{
}
