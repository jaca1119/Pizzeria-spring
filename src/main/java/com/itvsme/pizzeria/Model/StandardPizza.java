package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class StandardPizza
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Addon> addons;

    public StandardPizza(String name, Set<Addon> addons)
    {
        this.name = name;
        this.addons = addons;
    }
}
