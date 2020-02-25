package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.OrderPizzaCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPizzaCartRepository extends JpaRepository<OrderPizzaCart, Integer>
{
}
