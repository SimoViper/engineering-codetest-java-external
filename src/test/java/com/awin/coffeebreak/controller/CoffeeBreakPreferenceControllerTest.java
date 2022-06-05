package com.awin.coffeebreak.controller;


import com.awin.coffeebreak.dto.*;
import com.awin.coffeebreak.service.CoffeeBreakService;
import com.awin.coffeebreak.service.ResponseConversionService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class CoffeeBreakPreferenceControllerTest {

    private static final String KHILAN = "Khilan";
    private static final String JSON_RESPONSE = "{\n" +
            "    \"preferences\": [\n" +
            "        {\n" +
            "            \"orders\": [\n" +
            "                {\n" +
            "                    \"type\": \"FOOD\",\n" +
            "                    \"subType\": \"TOAST\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"requestedBy\": \"Khilan\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeBreakService coffeeBreakService;

    @MockBean
    private ResponseConversionService responseConversionService;


    @Test
    public void testToday_shouldReturnJsonBody() throws Exception {
        given(coffeeBreakService.getTodayPreferences()).willReturn(getTestPreferences());
        given(responseConversionService.getJsonForResponse(any(Preferences.class))).willReturn(JSON_RESPONSE);

        this.mockMvc.perform(get("/today?format=json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preferences[0].requestedBy", CoreMatchers.is(KHILAN)));
    }


    private List<Preference> getTestPreferences(){
        return Collections.singletonList(Preference.builder()
                        .requestedBy(KHILAN)
                        .orders(Collections.singletonList(Order.builder()
                                .type(Type.FOOD)
                                .subType(SubType.TOAST)
                                .build()))
                        .build());
    }
}
