package com.itvsme.pizzeria.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "order_id")
public class OrderComposedPizza extends OrderPizza
{

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderPizza_id")
    private Set<AddonInput> addonInputs;

    public OrderComposedPizza(String name, String surname, String phone, Set<AddonInput> addonInputs)
    {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.addonInputs = addonInputs;
    }

    @Override
    public String toString()
    {
        return "OrderComposedPizza{" +
                "addonInputs=" + addonInputs +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
