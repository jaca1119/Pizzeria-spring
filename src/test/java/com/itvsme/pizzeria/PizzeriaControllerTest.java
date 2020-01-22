package com.itvsme.pizzeria;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class PizzeriaControllerTest
{
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PizzeriaService pizzeriaService;

    @Test
    void getAllAddonsTest() throws Exception
    {
        List<Addon> addonList = new ArrayList<>();
        addonList.add(new Addon("onion", 3L));
        addonList.add(new Addon("pepper", 3L));
        when(pizzeriaService.findAll()).thenReturn(addonList);

        mockMvc.perform(MockMvcRequestBuilders.get("/addons")
        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());

    }
}
