package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.StandardPizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardPizzaRepository extends JpaRepository<StandardPizza, Integer>
{
}
