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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        ).andExpect(jsonPath("$", hasSize(2)))
                .andExpect(header().string("Cache-control", "max-age=3600")).andDo(print());
    }

}