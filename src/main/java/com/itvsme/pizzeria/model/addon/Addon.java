package com.itvsme.pizzeria.model.addon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor
public class Addon
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private int price;

    public Addon(String name, int price)
    {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Addon addon = (Addon) o;
        return Float.compare(addon.price, price) == 0 &&
                name.equals(addon.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, price);
    }

    @Override
    public String toString()
    {
        return "Addon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
