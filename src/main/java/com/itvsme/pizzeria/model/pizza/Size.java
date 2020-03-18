package com.itvsme.pizzeria.model.pizza;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Size
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int sizeInCm;
    private float priceMultiplier;

    public Size(int sizeInCm, float priceMultiplier)
    {
        this.sizeInCm = sizeInCm;
        this.priceMultiplier = priceMultiplier;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Size size = (Size) o;
        return sizeInCm == size.sizeInCm &&
                Float.compare(size.priceMultiplier, priceMultiplier) == 0;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(sizeInCm, priceMultiplier);
    }

    @Override
    public String toString()
    {
        return "Size{" +
                "id=" + id +
                ", sizeInCm=" + sizeInCm +
                ", priceMultiplier=" + priceMultiplier +
                '}';
    }
}
