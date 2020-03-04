package com.itvsme.pizzeria.model.pizza;

import com.itvsme.pizzeria.model.addon.AddonInput;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString
@PrimaryKeyJoinColumn(name = "pizza_id")
public class ComposedPizza extends Pizza
{
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "composed_pizza_id")
    private Set<AddonInput> addonsInput;

    public ComposedPizza(Set<AddonInput> addonsInput)
    {
        this.addonsInput = addonsInput;
    }
}
