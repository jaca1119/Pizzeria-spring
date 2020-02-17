package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor
public class AddonInput
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int amount;


    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addon_id")
    private Addon addon;

    public AddonInput(Addon addon, int amount)
    {
        this.amount = amount;
        this.addon = addon;
    }

    public AddonInput(String name, long price, int amount)
    {
        this.addon = new Addon(name, price);
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddonInput that = (AddonInput) o;
        return amount == that.amount &&
                addon.equals(that.addon);
    }

    @Override
    public String toString()
    {
        return "AddonInput{" +
                "id=" + id +
                ", amount=" + amount +
                ", addon=" + addon +
                '}';
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(amount, addon);
    }
}
