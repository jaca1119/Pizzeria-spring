package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderPizza_id")
    private Set<AddonInput> addonInputs;

    public OrderPizza(String customerName, String customerSurname, String phoneNumber, Set<AddonInput> addonInputs)
    {
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.phoneNumber = phoneNumber;
        this.addonInputs = addonInputs;
    }
}
