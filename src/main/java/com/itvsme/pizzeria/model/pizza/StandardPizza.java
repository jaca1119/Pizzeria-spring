package com.itvsme.pizzeria.model.pizza;

import com.itvsme.pizzeria.model.addon.Addon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Currency;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
@PrimaryKeyJoinColumn(name = "pizza_id")
public class StandardPizza extends Pizza
{
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Addon> addons;

    public StandardPizza(String name, Set<Addon> addons)
    {
        this.name = name;
        this.addons = addons;
    }
}
