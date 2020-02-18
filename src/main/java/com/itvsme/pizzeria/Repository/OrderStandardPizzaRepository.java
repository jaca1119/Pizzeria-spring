package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.OrderStandardPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStandardPizzaRepository extends CrudRepository<OrderStandardPizza, Integer>
{
}
