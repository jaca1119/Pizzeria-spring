package com.itvsme.pizzeria.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
@ToString
@PrimaryKeyJoinColumn(name = "order_id")
public class OrderStandardPizza extends OrderPizza
{

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "standard_id")
    private StandardPizza standardPizza;

    public OrderStandardPizza(String name, String surname, String phone, StandardPizza standardPizza)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.standardPizza = standardPizza;
    }


}
