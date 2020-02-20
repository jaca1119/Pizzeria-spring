package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString
public class ComposedPizza
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @ManyToMany(cascade = CascadeType.PERSIST)
    protected List<Addon> addons;

    public ComposedPizza(List<Addon> addons)
    {
        this.addons= addons;
    }
}
