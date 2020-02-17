package com.itvsme.pizzeria.Repository;

import com.itvsme.pizzeria.Model.AddonInput;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddonInputRepository extends CrudRepository<AddonInput, Integer>
{
}
