package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.OrderComposedPizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderComposedPizzaRepository extends CrudRepository<OrderComposedPizza, Integer>
{
}
