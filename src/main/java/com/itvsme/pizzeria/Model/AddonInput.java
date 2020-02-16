package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddonInput that = (AddonInput) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(amount);
    }
}
