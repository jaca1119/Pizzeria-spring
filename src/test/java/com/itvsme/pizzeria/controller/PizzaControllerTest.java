package com.itvsme.pizzeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.model.pizza.ComposedPizza;
import com.itvsme.pizzeria.model.pizza.StandardPizza;
import com.itvsme.pizzeria.repository.RequestLogRepository;
import com.itvsme.pizzeria.service.ComposedPizzaService;
import com.itvsme.pizzeria.service.StandardPizzaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(PizzaController.class)
public class PizzaControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StandardPizzaService standardPizzaService;
    @MockBean
    private ComposedPizzaService composedPizzaService;

    @MockBean
    private RequestLogRepository requestLogRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

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
    @WithMockUser(roles = {"ADMIN"})
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
                .andExpect(jsonPath("$.standard_pizza.addons[0].name").value("pepper"))
                .andExpect(jsonPath("$.standard_pizza.addons[0].price").value(300))
                .andExpect(jsonPath("$.standard_pizza.addons[1].name").value("onion"))
                .andExpect(jsonPath("$.standard_pizza.addons[1].price").value(300))
                .andExpect(jsonPath("$.standard_pizza.priceIntegralMultipleValue").value(1000))
        .andDo(print());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void saveComposedPizzaTest() throws Exception
    {
        ComposedPizza composedPizza = givenComposedPizza();

        when(composedPizzaService.saveComposedPizza(any(ComposedPizza.class))).thenReturn(composedPizza);

        String composedPizzaJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(composedPizza);

        ResultActions result = mockMvc.perform(post("/composed")
        .contentType(MediaType.APPLICATION_JSON)
        .content(composedPizzaJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.composed_pizza.addonsInput[0].addon.name").value("onion"))
                .andExpect(jsonPath("$.composed_pizza.addonsInput[0].addon.price").value(300))
                .andExpect(jsonPath("$.composed_pizza.addonsInput[1].addon.name").value("mice"))
                .andExpect(jsonPath("$.composed_pizza.addonsInput[1].addon.price").value(300))
                .andExpect(jsonPath("$.composed_pizza.size.sizeInCm").value(25))
                .andExpect(jsonPath("$.composed_pizza.size.priceMultiplier").value(1.1F));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteByIdStandardPizza() throws Exception
    {
        mockMvc.perform(delete("/standard/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());
    }
}
