package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.addon.Addon;
import com.itvsme.pizzeria.service.AddonsService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class AddonController
{
    private AddonsService addonsService;

    public AddonController(AddonsService addonsService)
    {
        this.addonsService = addonsService;
    }

    @GetMapping("/addons")
    public ResponseEntity<List<Addon>> getAllAddons()
    {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS)).body(addonsService.findAll());
    }
}
