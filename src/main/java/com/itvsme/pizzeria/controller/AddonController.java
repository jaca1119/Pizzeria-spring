package com.itvsme.pizzeria.controller;

import com.itvsme.pizzeria.model.addon.Addon;
import com.itvsme.pizzeria.service.AddonsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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
        return new ResponseEntity<>(addonsService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/addons/{id}")
    public ResponseEntity<Addon> getAddonById(@PathVariable Integer id)
    {
        return new ResponseEntity<>(addonsService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/addons")
    public ResponseEntity<Addon> createAddon(@RequestBody Addon addon)
    {
        return new ResponseEntity<>(addonsService.save(addon), HttpStatus.CREATED);
    }

    @DeleteMapping("/addons/{id}")
    public ResponseEntity<Void> deleteAddon(@PathVariable Integer id)
    {
        return new ResponseEntity<>(addonsService.deleteById(id), HttpStatus.OK);
    }
}
