package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "order_id")
public class OrderPizzaCart extends OrderPizza
{
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_pizza_with_list_id")
    private List<OrderPizza> orderPizzas;


    public OrderPizzaCart(String name, String surname, String phone, List<OrderPizza> orderPizzas)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.orderPizzas = orderPizzas;
    }

    @Override
    public String toString()
    {
        return "OrderPizzaCart{" +
                "orderPizzas=" + orderPizzas +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
