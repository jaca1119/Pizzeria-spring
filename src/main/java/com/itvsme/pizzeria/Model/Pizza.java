package com.itvsme.pizzeria.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pizza
{
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Addon> addons;


    public Pizza(String name, List<Addon> addons)
    {
        this.name = name;
        this.addons = addons;
    }

    public Pizza()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Addon> getAddons()
    {
        return addons;
    }

    public void setAddons(List<Addon> addons)
    {
        this.addons = addons;
    }
}
