package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.OrderPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPizzaRepository extends JpaRepository<OrderPizza, Integer>
{
}
