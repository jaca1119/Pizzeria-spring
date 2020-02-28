package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.Addon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddonRepository extends CrudRepository<Addon, Integer>
{
    Optional<Addon> findByName(String name);
}
