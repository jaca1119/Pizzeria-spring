package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class OrderPizza
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customerName;
    private String customerSurname;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId
    private ComposedPizza orderedPizza;


    public OrderPizza(String customer_name, String customer_surname, String phoneNumber, ComposedPizza orderedPizza)
    {
        this.customerName = customer_name;
        this.customerSurname = customer_surname;
        this.phoneNumber = phoneNumber;
        this.orderedPizza = orderedPizza;
    }
}
