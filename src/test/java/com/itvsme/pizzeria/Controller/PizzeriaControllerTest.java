package com.itvsme.pizzeria.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Repository.OrderPizzaRepository;
import com.itvsme.pizzeria.Service.AddonsService;
import com.itvsme.pizzeria.Service.ComposedPizzaService;
import com.itvsme.pizzeria.Service.OrderPizzaService;
import com.itvsme.pizzeria.Service.StandardPizzaService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
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

    @Test
    void getOneAddon() throws Exception
    {
        Addon addon = givenAddon();

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
        addonList.add(givenAddon());
        addonList.add(givenAddon());
        when(addonsService.findAll()).thenReturn(addonList);

        mockMvc.perform(MockMvcRequestBuilders.get("/addons")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void successAddonAddTest() throws Exception
    {
        Addon addon = givenAddon();
        when(addonsService.save(any(Addon.class))).thenReturn(addon);

        String addonToJson = objectMapper.writeValueAsString(addon);

        ResultActions result = mockMvc.perform(post("/addons")
        .contentType(MediaType.APPLICATION_JSON)
        .content(addonToJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("cucumber"))
                .andExpect(jsonPath("$.price").value(2L));
    }

    @Test
    void getAllStandardPizzas() throws Exception
    {
        List<StandardPizza> standardPizzaList = new ArrayList<>();

        standardPizzaList.add(givenStandardPizzaMargherita());
        standardPizzaList.add(givenStandardPizzaMargherita());

        when(standardPizzaService.findAll()).thenReturn(standardPizzaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/standard")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void saveStandardPizzaTest() throws Exception
    {
        StandardPizza standardPizza = givenStandardPizzaMargherita();

        when(standardPizzaService.save(any(StandardPizza.class))).thenReturn(standardPizza);

        String standardPizzaJSON = objectMapper.writeValueAsString(standardPizza);

        mockMvc.perform(
                post("/standard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(standardPizzaJSON)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.standard_pizza.name").value("Margherita"))
                .andExpect(jsonPath("$.standard_pizza.addons[0].name").value("onion"))
                .andExpect(jsonPath("$.standard_pizza.addons[0].price").value(3L))
                .andExpect(jsonPath("$.standard_pizza.addons[1].name").value("pepper"))
                .andExpect(jsonPath("$.standard_pizza.addons[1].price").value(3L));
    }

    @Test
    void saveComposedPizzaTest() throws Exception
    {
        ComposedPizza composedPizza = givenComposedPizza();

        when(composedPizzaService.saveComposedPizza(any(ComposedPizza.class))).thenReturn(composedPizza);

        String composedPizzaJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(composedPizza);

        ResultActions result = mockMvc.perform(post("/composed")
        .contentType(MediaType.APPLICATION_JSON)
        .content(composedPizzaJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.composed_pizza.addonsInput[0].addon.name").value("mice"))
                .andExpect(jsonPath("$.composed_pizza.addonsInput[0].addon.price").value(3L))
                .andExpect(jsonPath("$.composed_pizza.addonsInput[1].addon.name").value("onion"))
                .andExpect(jsonPath("$.composed_pizza.addonsInput[1].addon.price").value(3L));
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
        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        OrderComposedPizza sampleOrder = givenOrderComposedPizza();


        List<OrderComposedPizza> allOrderComposedPizza = Stream.of(orderComposedPizza, sampleOrder).collect(Collectors.toList());

        when(composedPizzaService.findAllOrderPizza()).thenReturn(allOrderComposedPizza);

        mockMvc.perform(get("/orders-composed")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void addOrderPizza() throws Exception
    {
        OrderComposedPizza sampleOrder = givenOrderComposedPizza();

        when(composedPizzaService.saveOrder(any(OrderComposedPizza.class))).thenReturn(sampleOrder);

        String sampleOrderJson = objectMapper.writeValueAsString(sampleOrder);

        MvcResult mvcResult = mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleOrderJson)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Customer name")))
                .andExpect(jsonPath("$.surname", is("Customer surname")))
                .andExpect(jsonPath("$.phone", is("phonenumber")))
                .andDo(print())
                .andReturn();

        assertThat(sampleOrderJson).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void addOrderPizzaWithInputAddon() throws Exception
    {
        OrderComposedPizza sampleOrder = givenOrderComposedPizza();

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
        OrderStandardPizza orderStandardPizza = givenOrderStandardPizzaMargherita();

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
        OrderStandardPizza orderStandardPizza =givenOrderStandardPizzaMargherita();
        OrderStandardPizza sample = givenOrderStandardPizzaSample();

        when(standardPizzaService.findAllOrdersStandard()).thenReturn(Lists.list(orderStandardPizza, sample));

        mockMvc.perform(get("/orders-standard")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    void getAllOrders() throws Exception
    {
        OrderStandardPizza orderStandardPizza = givenOrderStandardPizzaMargherita();
        OrderStandardPizza sample = givenOrderStandardPizzaSample();

        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();


        when(orderpizzaRepository.findAll()).thenReturn(Lists.list(orderStandardPizza, sample, orderComposedPizza));

        mockMvc.perform(get("/all-orders")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    void saveAllOrdersPizza() throws Exception
    {
        OrderStandardPizza orderStandardPizza = givenOrderStandardPizzaMargherita();
        OrderComposedPizza orderComposedPizza = givenOrderComposedPizza();

        List<OrderPizza> orderPizzaList = Lists.list(orderStandardPizza, orderComposedPizza);

        when(orderPizzaService.saveAll(ArgumentMatchers.anyIterable())).thenReturn(orderPizzaList);

        String orderJson = objectMapper.writeValueAsString(orderPizzaList);

        MvcResult result = mockMvc.perform(post("/orders-pizza")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        ).andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        assertThat(orderJson).isEqualToIgnoringWhitespace(result.getResponse().getContentAsString());
    }

    @Test
    void saveOrderPizzaCart() throws Exception
    {
        OrderPizzaCart orderPizzaCart = givenOrderPizzaCart();

        when(orderPizzaService.saveOrderPizzaCart(any(OrderPizzaCart.class))).thenReturn(orderPizzaCart);

        String orderJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderPizzaCart);

        MvcResult mvcResult = mockMvc.perform(post("/order-pizza-cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        ).andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(orderJson);
    }
}
