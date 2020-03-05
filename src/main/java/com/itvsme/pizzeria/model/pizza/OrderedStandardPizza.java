package com.itvsme.pizzeria.model.pizza;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter @Setter
@PrimaryKeyJoinColumn(name = "pizza_id")
public class OrderedStandardPizza extends Pizza
{
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "standard_pizza_id")
    private StandardPizza standardPizza;

    public OrderedStandardPizza(Size size, StandardPizza standardPizza)
    {
        this.size = size;
        this.standardPizza = standardPizza;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedStandardPizza that = (OrderedStandardPizza) o;
        return size.equals(that.size) &&
                standardPizza.equals(that.standardPizza);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(size, standardPizza);
    }

    @Override
    public String toString()
    {
        return "OrderedStandardPizza{" +
                "size=" + size +
                ", standardPizza=" + standardPizza +
                '}';
    }
}
