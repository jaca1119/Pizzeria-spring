package com.itvsme.pizzeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.model.addon.Addon;
import com.itvsme.pizzeria.repository.RequestLogRepository;
import com.itvsme.pizzeria.service.AddonsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.givenAddon;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(value = AddonController.class)
class AddonControllerTest
{
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AddonsService addonsService;
    @MockBean
    private RequestLogRepository requestLogRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllAddons() throws Exception
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
    void getAddonById() throws Exception
    {
        Addon addon = givenAddon();

        when(addonsService.findById(any(Integer.class))).thenReturn(addon);

        mockMvc.perform(get("/addons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("cucumber"))
                .andExpect(jsonPath("$.price").value(200));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void createAddon() throws Exception
    {
        Addon addon = givenAddon();
        when(addonsService.save(any(Addon.class))).thenReturn(addon);

        String addonToJson = objectMapper.writeValueAsString(addon);

        ResultActions result = mockMvc.perform(post("/addons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addonToJson));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("cucumber"))
                .andExpect(jsonPath("$.price").value(200));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteAddonById() throws Exception
    {
        mockMvc.perform(delete("/addons/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}