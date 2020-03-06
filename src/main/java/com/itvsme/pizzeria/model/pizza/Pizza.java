package com.itvsme.pizzeria.model.pizza;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StandardPizza.class, name = "standard_pizza"),
        @JsonSubTypes.Type(value = ComposedPizza.class, name = "composed_pizza"),
        @JsonSubTypes.Type(value = OrderedStandardPizza.class, name = "ordered_standard_pizza")

})
@Entity
@Getter @Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Pizza
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
