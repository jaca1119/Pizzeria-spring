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

    @Column(name = "price")
    private int priceIntegralMultipleValue;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Addon> addons;

    public StandardPizza(String name, Set<Addon> addons, int priceIntegralMultipleValue)
    {
        this.name = name;
        this.addons = addons;
        this.priceIntegralMultipleValue = priceIntegralMultipleValue;
    }
}
