package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.Addon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddonRepository extends JpaRepository<Addon, Integer>
{
}
