package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.AddonInput;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddonInputRepository extends CrudRepository<AddonInput, Integer>
{
}
