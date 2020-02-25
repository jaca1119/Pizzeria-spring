package com.itvsme.pizzeria.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Repository.OrderPizzaRepository;
import com.itvsme.pizzeria.Service.AddonsService;
import com.itvsme.pizzeria.Service.ComposedPizzaService;
import com.itvsme.pizzeria.Service.OrderPizzaService;
import com.itvsme.pizzeria.Service.StandardPizzaService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebMvcTest
public class PizzeriaControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AddonsService addonsService;

    @MockBean
    private StandardPizzaService standardPizzaService;

    @MockBean
    private ComposedPizzaService composedPizzaService;

    @MockBean
    private OrderPizzaRepository orderpizzaRepository;
    @MockBean
    private OrderPizzaService orderPizzaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Addon onion;
    private Addon pepper;
    private Addon mice;
    private AddonInput onionInput;
    private AddonInput pepperInput;
    private AddonInput miceInput;

    @BeforeEach
    void setUp()
    {
        onion = new Addon("onion", 3L);
        pepper = new Addon("pepper", 3L);
        mice = new Addon("mice", 3L);
        onionInput = new AddonInput(onion,2);
        pepperInput = new AddonInput(pepper, 1);
        miceInput = new AddonInput(mice, 2);
    }



    @Test
    void getOneAddon() throws Exception
    {
        Addon addon = new Addon("cucumber", 2L);

        when(addonsService.findById(any(Integer.class))).thenReturn(addon);

        mockMvc.perform(get("/addons/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("cucumber"))
                .andExpect(jsonPath("$.price").value(2L));
    }

    @Test
    void getAllAddonsTest() throws Exception
    {
        List<Addon> addonList = new ArrayList<>();
        addonList.add(new Addon("onion", 3L));
        addonList.add(new Addon("pepper", 3L));
        when(addonsService.findAll()).thenReturn(addonList);

        mockMvc.perform(MockMvcRequestBuilders.get("/addons")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void successAddonAddTest() throws Exception
    {
        Addon addon = new Addon("maize", 3L);
        when(addonsService.save(any(Addon.class))).thenReturn(addon);

        String addonToJson = objectMapper.writeValueAsString(addon);

        ResultActions result = mockMvc.perform(post("/addons")
        .contentType(MediaType.APPLICATION_JSON)
        .content(addonToJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("maize"))
                .andExpect(jsonPath("$.price").value(3L));
    }

    @Test
    void getAllStandardPizzas() throws Exception
    {
        List<StandardPizza> standardPizzaList = new ArrayList<>();

        standardPizzaList.add(new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet())));
        standardPizzaList.add(new StandardPizza("Sample", Stream.of(onion).collect(Collectors.toSet())));

        when(standardPizzaService.findAll()).thenReturn(standardPizzaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/standard")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void saveStandardPizzaTest() throws Exception
    {
        StandardPizza standardPizza = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        when(standardPizzaService.save(any(StandardPizza.class))).thenReturn(standardPizza);

        String standardPizzaJSON = objectMapper.writeValueAsString(standardPizza);

        mockMvc.perform(
                post("/standard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(standardPizzaJSON)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Margherita"))
                .andExpect(jsonPath("$.addons[0].name").value("onion"))
                .andExpect(jsonPath("$.addons[0].price").value(3L))
                .andExpect(jsonPath("$.addons[1].name").value("pepper"))
                .andExpect(jsonPath("$.addons[1].price").value(3L));
    }

    @Test
    void saveComposedPizzaTest() throws Exception
    {
        ComposedPizza composedPizza = new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet()));

        when(composedPizzaService.saveComposedPizza(any(ComposedPizza.class))).thenReturn(composedPizza);

        String composedPizzaJson = objectMapper.writeValueAsString(composedPizza);

        ResultActions result = mockMvc.perform(post("/composed")
        .contentType(MediaType.APPLICATION_JSON)
        .content(composedPizzaJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.addonsInput[0].addon.name").value("pepper"))
                .andExpect(jsonPath("$.addonsInput[0].addon.price").value(3L))
                .andExpect(jsonPath("$.addonsInput[1].addon.name").value("onion"))
                .andExpect(jsonPath("$.addonsInput[1].addon.price").value(3L));
    }

    @Test
    void deleteByIdAddon() throws Exception
    {
        mockMvc.perform(delete("/addons/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isOk());
    }

    @Test
    void deleteByIdStandardPizza() throws Exception
    {
        mockMvc.perform(delete("/standard/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());
    }

    @Test
    void findAllOrderPizza() throws Exception
    {
        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(onionInput, pepperInput).collect(Collectors.toSet())));

        OrderComposedPizza sampleOrder = new OrderComposedPizza("Customer name sample",
                "Customer surname sample",
                "sample",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));


        List<OrderComposedPizza> allOrderComposedPizza = Stream.of(orderComposedPizza, sampleOrder).collect(Collectors.toList());

        when(composedPizzaService.findAllOrderPizza()).thenReturn(allOrderComposedPizza);

        mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void addOrderPizza() throws Exception
    {
        AddonInput onion = new AddonInput("onion", 3L, 2);
        AddonInput pepper = new AddonInput("pepper", 3L, 1);
        AddonInput ham = new AddonInput("ham", 3L, 2);


        OrderComposedPizza sampleOrder = new OrderComposedPizza("Customer name sample",
                "Customer surname sample",
                "sample",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        when(composedPizzaService.saveOrder(any(OrderComposedPizza.class))).thenReturn(sampleOrder);

        String sampleOrderJson = objectMapper.writeValueAsString(sampleOrder);

        MvcResult mvcResult = mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleOrderJson)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Customer name sample")))
                .andExpect(jsonPath("$.surname", is("Customer surname sample")))
                .andExpect(jsonPath("$.phone", is("sample")))
                .andDo(print())
                .andReturn();

        assertThat(sampleOrderJson).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void addOrderPizzaWithInputAddon() throws Exception
    {
        AddonInput onion = new AddonInput("onion", 3L, 2);

        OrderComposedPizza sampleOrder = new OrderComposedPizza("Customer name sample",
                "Customer surname sample",
                "sample",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        when(composedPizzaService.saveOrder(any(OrderComposedPizza.class))).thenReturn(sampleOrder);

        String sampleOrderJson = objectMapper.writeValueAsString(sampleOrder);

        MvcResult mvcResult = mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleOrderJson)
        ).andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(sampleOrderJson).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void addOrderStandardPizza() throws Exception
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        when(standardPizzaService.saveOrderStandardPizza(any(OrderStandardPizza.class))).thenReturn(orderStandardPizza);

        String orderJson = objectMapper.writeValueAsString(orderStandardPizza);

        MvcResult mvcResult = mockMvc.perform(post("/ordersStandard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        ).andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.surname", is("surname")))
                .andExpect(jsonPath("$.phone", is("phone")))
                .andReturn();

        assertThat(orderJson).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAllOrderStandard() throws Exception
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));
        StandardPizza samplePizza = new StandardPizza("Sample", Stream.of(onion).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name", "surname", "phone", margherita);
        OrderStandardPizza sample = new OrderStandardPizza("sample", "sample", "123", samplePizza);

        when(standardPizzaService.findAllOrdersStandard()).thenReturn(Lists.list(orderStandardPizza, sample));

        mockMvc.perform(get("/ordersStandard")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    void getAllOrders() throws Exception
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));
        StandardPizza samplePizza = new StandardPizza("Sample", Stream.of(onion).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name", "surname", "phone", margherita);
        OrderStandardPizza sample = new OrderStandardPizza("sample", "sample", "123", samplePizza);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));


        when(orderpizzaRepository.findAll()).thenReturn(Lists.list(orderStandardPizza, sample, orderComposedPizza));

        mockMvc.perform(get("/allorders")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    void saveAllOrdersPizza() throws Exception
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        List<OrderPizza> orderPizzaList = Lists.list(orderStandardPizza, orderComposedPizza);

        when(orderPizzaService.saveAll(ArgumentMatchers.anyIterable())).thenReturn(orderPizzaList);

        String orderJson = objectMapper.writeValueAsString(orderPizzaList);

        MvcResult result = mockMvc.perform(post("/orders-pizza")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        ).andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }

    @Test
    void saveOrderPizzaCart() throws Exception
    {
        StandardPizza margherita = new StandardPizza("Margherita", Stream.of(onion, pepper).collect(Collectors.toSet()));

        OrderStandardPizza orderStandardPizza = new OrderStandardPizza("name",
                "surname",
                "phone",
                margherita);

        OrderComposedPizza orderComposedPizza = new OrderComposedPizza("Customer name",
                "Customer surname",
                "phonenumber",
                new ComposedPizza(Stream.of(miceInput, onionInput).collect(Collectors.toSet())));

        List<OrderPizza> orderPizzaList = Lists.list(orderStandardPizza, orderComposedPizza);

        OrderPizzaCart orderPizzaCart = new OrderPizzaCart("name", "surname", "phone", orderPizzaList);

        when(orderPizzaService.saveOrderPizzaCart(any(OrderPizzaCart.class))).thenReturn(orderPizzaCart);

        String orderJson = objectMapper.writeValueAsString(orderPizzaCart);

        MvcResult mvcResult = mockMvc.perform(post("/order-pizza-carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        ).andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(orderJson);
    }
}
