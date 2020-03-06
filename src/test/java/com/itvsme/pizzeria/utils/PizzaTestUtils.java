package com.itvsme.pizzeria.utils;

import com.itvsme.pizzeria.model.addon.Addon;
import com.itvsme.pizzeria.model.addon.AddonInput;
import com.itvsme.pizzeria.model.order.OrderPizzaCart;
import com.itvsme.pizzeria.model.pizza.ComposedPizza;
import com.itvsme.pizzeria.model.pizza.OrderedStandardPizza;
import com.itvsme.pizzeria.model.pizza.Size;
import com.itvsme.pizzeria.model.pizza.StandardPizza;
import org.assertj.core.util.Lists;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class  PizzaTestUtils
{
    public static StandardPizza givenStandardPizzaMargherita()
    {
        return new StandardPizza("Margherita", Stream.of(new Addon("onion", 300), new Addon("pepper", 300)).collect(Collectors.toSet()), 1000);
    }

    public static StandardPizza givenStandardPizzaSample()
    {
        return new StandardPizza("Sample", Stream.of( new Addon("onion", 3)).collect(Collectors.toSet()), 1000);
    }

    public static OrderedStandardPizza givenOrderedStandardPizza()
    {
        return new OrderedStandardPizza(new Size(25, 1.1F), givenStandardPizzaMargherita());
    }

    public static ComposedPizza givenComposedPizza()
    {
        return new ComposedPizza(new Size(25, 1.1F) ,Stream.of(new AddonInput(new Addon("mice", 300), 2),new AddonInput(new Addon("onion", 300),2)).collect(Collectors.toSet()));
    }

    public static ComposedPizza givenComposedPizzaDifferent()
    {
        return new ComposedPizza(new Size(25, 1.1F), Stream.of(new AddonInput(new Addon("pepper", 300), 1),new AddonInput(new Addon("onion", 300),2)).collect(Collectors.toSet()));
    }

    public static Size givenSize()
    {
        return new Size(25, 1.1F);
    }

    public static OrderPizzaCart givenOrderPizzaCart()
    {
        return new OrderPizzaCart("name", "surname", "phone", Lists.list(givenStandardPizzaMargherita(), givenComposedPizza()));
    }

    public static OrderPizzaCart givenOrderPizzaCartOrderedStandardPizza()
    {
        return new OrderPizzaCart("name", "surname", "phone", Lists.list(givenOrderedStandardPizza(), givenComposedPizza()));
    }

    public static Addon givenAddon()
    {
        return new Addon("cucumber", 200);
    }
}
