package com.itvsme.pizzeria.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.Model.Addon;
import com.itvsme.pizzeria.Model.Pizza;
import com.itvsme.pizzeria.Service.AddonsService;
import com.itvsme.pizzeria.Service.PizzaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest
public class PizzeriaControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AddonsService addonsService;

    @MockBean
    private PizzaService pizzaService;

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

        ObjectMapper objectMapper = new ObjectMapper();
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
        List<Pizza> pizzaList = new ArrayList<>();
        Addon[] margheritaAddons = {new Addon("onion", 3L), new Addon("pepper", 3L)};
        Addon[] sampleAddons = {new Addon("onion", 3L), new Addon("pepper", 3L), new Addon("maize", 3L)};

        pizzaList.add(new Pizza("Margherita", Arrays.asList(margheritaAddons)));
        pizzaList.add(new Pizza("Sample", Arrays.asList(sampleAddons)));

        when(pizzaService.findAll()).thenReturn(pizzaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/standard")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}
