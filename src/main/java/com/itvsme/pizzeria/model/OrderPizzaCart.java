package com.itvsme.pizzeria.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "order_pizza_id")
public class OrderPizzaCart extends OrderPizza
{
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_pizza_cart_id")
    private List<Pizza> pizzas;


    public OrderPizzaCart(String name, String surname, String phone, List<Pizza> pizzas)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.pizzas = pizzas;
    }

    @Override
    public String toString()
    {
        return "OrderPizzaCart{" +
                "pizzas=" + pizzas +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
