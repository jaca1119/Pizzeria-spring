package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer>
{
}
