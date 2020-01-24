package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class ComposedPizza
{
    @Id
    @GeneratedValue
    protected Integer id;
    @OneToMany(cascade = CascadeType.PERSIST)
    protected List<Addon> addons;

    public ComposedPizza(List<Addon> addons)
    {
        this.addons= addons;
    }
}
