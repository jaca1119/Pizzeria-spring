package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@NoArgsConstructor
public class AddonInput extends Addon
{
    private int amount;

    public AddonInput(String name, long price, int amount)
    {
        super(name, price);
        this.amount = amount;
    }
}
