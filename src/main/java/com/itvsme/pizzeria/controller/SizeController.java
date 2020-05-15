package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.pizza.Size;
import com.itvsme.pizzeria.repository.SizeRepository;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class SizeController
{
    private SizeRepository sizeRepository;

    public SizeController(SizeRepository sizeRepository)
    {
        this.sizeRepository = sizeRepository;
    }

    @GetMapping("/sizes")
    public ResponseEntity<List<Size>> getAllSizes()
    {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS)).body(sizeRepository.findAll());
    }

}
