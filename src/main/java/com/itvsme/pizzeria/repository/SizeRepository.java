package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.pizza.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer>
{
    Optional<Size> findBySizeInCm(int sizeInCm);
}
