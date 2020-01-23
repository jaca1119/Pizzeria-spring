package com.itvsme.pizzeria.Model;

import javax.persistence.*;

@Entity
public class Addon
{
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private float price;

    public Addon(String name, long price)
    {
        this.name = name;
        this.price = price;
    }

    public Addon()
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

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }
}
