package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.Addon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddonRepository extends CrudRepository<Addon, Integer>
{
    Optional<Addon> findByName(String name);
}
