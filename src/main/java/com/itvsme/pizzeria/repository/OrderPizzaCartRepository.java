package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.OrderPizzaCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPizzaCartRepository extends JpaRepository<OrderPizzaCart, Integer>
{
}
