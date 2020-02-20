package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString
public class Addon
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private float price;

    public Addon(String name, long price)
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
        return name.equals(addon.name);
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
