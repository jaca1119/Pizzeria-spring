package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "order_id")
public class OrderComposedPizza extends OrderPizza
{

    @OneToOne(cascade = CascadeType.PERSIST)
    private ComposedPizza composedPizza;

    public OrderComposedPizza(String name, String surname, String phone, ComposedPizza composedPizza)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.composedPizza = composedPizza;
    }

    @Override
    public String toString()
    {
        return "OrderComposedPizza{" +
                "addonInputs=" + composedPizza +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
