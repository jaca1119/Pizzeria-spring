package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
public class OrderStandardPizza
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String surname;
    private String phone;
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
