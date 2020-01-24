package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class StandardPizza extends ComposedPizza
{
    private String name;

    public StandardPizza(String name, List<Addon> addons)
    {
        this.name = name;
        this.addons = addons;
    }
}
