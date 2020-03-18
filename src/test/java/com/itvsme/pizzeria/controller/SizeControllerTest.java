package com.itvsme.pizzeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.model.pizza.Size;
import com.itvsme.pizzeria.repository.RequestLogRepository;
import com.itvsme.pizzeria.repository.SizeRepository;
import com.itvsme.pizzeria.utils.PizzaTestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.givenSize;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@WebMvcTest(SizeController.class)
class SizeControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RequestLogRepository requestLogRepository;

    @MockBean
    private SizeRepository sizeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllSizesTest() throws Exception
    {
        List<Size> sizes = List.of(givenSize(), givenSize());

        when(sizeRepository.findAll()).thenReturn(sizes);

        mockMvc.perform(get("/sizes")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void saveSize() throws Exception
    {
        Size size = givenSize();

        when(sizeRepository.save(ArgumentMatchers.any(Size.class))).thenReturn(size);

        String sizeJson = objectMapper.writeValueAsString(size);

        mockMvc.perform(post("/sizes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(sizeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sizeInCm").value(size.getSizeInCm()))
                .andExpect(jsonPath("$.priceMultiplier").value(size.getPriceMultiplier()))
                .andDo(print());

    }
}