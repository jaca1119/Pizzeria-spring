package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.OrderPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPizzaRepository extends JpaRepository<OrderPizza, Integer>
{
}
