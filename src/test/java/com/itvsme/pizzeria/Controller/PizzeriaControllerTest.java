package com.itvsme.pizzeria.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.Model.*;
import com.itvsme.pizzeria.Service.AddonsService;
import com.itvsme.pizzeria.Service.ComposedPizzaService;
import com.itvsme.pizzeria.Service.StandardPizzaService;
import org.junit.jupiter.api.Test;
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

    private ObjectMapper objectMapper = new ObjectMapper();


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
        Addon[] margheritaAddons = {new Addon("onion", 3L), new Addon("pepper", 3L)};
        Addon[] sampleAddons = {new Addon("onion", 3L), new Addon("pepper", 3L), new Addon("maize", 3L)};

        standardPizzaList.add(new StandardPizza("Margherita", Arrays.asList(margheritaAddons)));
        standardPizzaList.add(new StandardPizza("Sample", Arrays.asList(sampleAddons)));

        when(standardPizzaService.findAll()).thenReturn(standardPizzaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/standard")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }

    @Test
    void saveStandardPizzaTest() throws Exception
    {
        Addon[] margheritaAddons = {new Addon("onion", 3L), new Addon("pepper", 3L)};

        StandardPizza standardPizza = new StandardPizza("Margherita", Arrays.asList(margheritaAddons));

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
        ComposedPizza composedPizza = new ComposedPizza(Stream.of(new Addon("Sample", 3L), new Addon("Sample_two", 2L)).collect(Collectors.toList()));

        when(composedPizzaService.saveComposedPizza(any(ComposedPizza.class))).thenReturn(composedPizza);

        String composedPizzaJson = objectMapper.writeValueAsString(composedPizza);

        ResultActions result = mockMvc.perform(post("/composed")
        .contentType(MediaType.APPLICATION_JSON)
        .content(composedPizzaJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.addons[0].name").value("Sample"))
                .andExpect(jsonPath("$.addons[0].price").value(3L))
                .andExpect(jsonPath("$.addons[1].name").value("Sample_two"))
                .andExpect(jsonPath("$.addons[1].price").value(2L));
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
        List<OrderPizza> allOrderPizza = new ArrayList<>();

        AddonInput onion = new AddonInput("onion", 3L, 2);
        AddonInput pepper = new AddonInput("pepper", 3L, 1);
        AddonInput ham = new AddonInput("ham", 3L, 2);

        OrderPizza orderPizza = new OrderPizza("Customer name", "Customer surname", "phonenumber", Stream.of(onion, pepper).collect(Collectors.toSet()));
        OrderPizza sampleOrder = new OrderPizza("Customer name sample", "Customer surname sample", "sample", Stream.of(onion, pepper, ham).collect(Collectors.toSet()));


        allOrderPizza.addAll(Stream.of(orderPizza, sampleOrder).collect(Collectors.toList()));

        when(composedPizzaService.findAllOrderPizza()).thenReturn(allOrderPizza);

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


        OrderPizza sampleOrder = new OrderPizza("Customer name sample", "Customer surname sample", "sample", Stream.of(onion, pepper, ham).collect(Collectors.toSet()));

        when(composedPizzaService.saveOrder(any(OrderPizza.class))).thenReturn(sampleOrder);

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
    void addOrderPizzaWithInputAddon() throws Exception
    {
        AddonInput onion = new AddonInput("onion", 3L, 2);


        OrderPizza sampleOrder = new OrderPizza("Customer name sample", "Customer surname sample", "sample", Stream.of(onion).collect(Collectors.toSet()));

        when(composedPizzaService.saveOrder(any(OrderPizza.class))).thenReturn(sampleOrder);

        String sampleOrderJson = objectMapper.writeValueAsString(sampleOrder);

        MvcResult mvcResult = mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sampleOrderJson)
        ).andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        assertThat(sampleOrderJson).isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }
}
