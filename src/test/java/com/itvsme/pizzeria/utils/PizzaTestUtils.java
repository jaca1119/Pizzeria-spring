package com.itvsme.pizzeria.utils;

import com.itvsme.pizzeria.Model.*;
import org.assertj.core.util.Lists;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class  PizzaTestUtils
{
    public static StandardPizza givenStandardPizzaMargherita()
    {
        return new StandardPizza("Margherita", Stream.of(new Addon("onion", 3L), new Addon("pepper", 3L)).collect(Collectors.toSet()));
    }

    public static StandardPizza givenStandardPizzaSample()
    {
        return new StandardPizza("Sample", Stream.of( new Addon("onion", 3L)).collect(Collectors.toSet()));
    }

    public static OrderStandardPizza givenOrderStandardPizzaMargherita()
    {
        return new OrderStandardPizza("name", "surname", "phone", givenStandardPizzaMargherita());
    }

    public static OrderStandardPizza givenOrderStandardPizzaSample()
    {
        return new OrderStandardPizza("name", "surname", "phone", givenStandardPizzaSample());
    }

    public static ComposedPizza givenComposedPizza()
    {
        return new ComposedPizza(Stream.of(new AddonInput(new Addon("mice", 3L), 2),new AddonInput(new Addon("onion", 3L),2)).collect(Collectors.toSet()));
    }

    public static ComposedPizza givenComposedPizzaDifferent()
    {
        return new ComposedPizza(Stream.of(new AddonInput(new Addon("pepper", 3L), 1),new AddonInput(new Addon("onion", 3L),2)).collect(Collectors.toSet()));
    }

    public static OrderPizzaCart givenOrderPizzaCart()
    {
        return new OrderPizzaCart("name", "surname", "phone", Lists.list(givenStandardPizzaMargherita(), givenComposedPizza()));
    }

    public static OrderComposedPizza givenOrderComposedPizza()
    {
        return new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                givenComposedPizza());
    }

    public static Addon givenAddon()
    {
        return new Addon("cucumber", 2L);
    }
}
