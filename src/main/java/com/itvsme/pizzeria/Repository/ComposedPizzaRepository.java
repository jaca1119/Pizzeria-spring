package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.ComposedPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposedPizzaRepository extends JpaRepository<ComposedPizza, Integer>
{
}
