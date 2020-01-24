package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
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
}
