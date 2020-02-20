package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString
public class ComposedPizza
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "composed_pizza_id")
    private Set<AddonInput> addonsInput;

    public ComposedPizza(Set<AddonInput> addonsInput)
    {
        this.addonsInput = addonsInput;
    }
}
