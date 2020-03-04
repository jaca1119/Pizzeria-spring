package com.itvsme.pizzeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itvsme.pizzeria.model.OrderPizzaCart;
import com.itvsme.pizzeria.repository.OrderPizzaRepository;
import com.itvsme.pizzeria.repository.RequestLogRepository;
import com.itvsme.pizzeria.service.OrderPizzaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.itvsme.pizzeria.utils.PizzaTestUtils.givenOrderPizzaCart;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderPizzaRepository orderpizzaRepository;
    @MockBean
    private OrderPizzaService orderPizzaService;
    @MockBean
    private RequestLogRepository requestLogRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createAllOrdersPizza()
    {
    }

    @Test
    void createOrderPizzaCart() throws Exception
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