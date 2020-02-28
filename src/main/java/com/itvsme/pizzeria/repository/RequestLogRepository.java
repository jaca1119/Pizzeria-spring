package com.itvsme.pizzeria.repository;

import com.itvsme.pizzeria.model.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Integer>
{
}
