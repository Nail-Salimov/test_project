package com.example.client;

import com.example.client.exception.DiscriminantException;
import com.fasterxml.jackson.databind.ObjectMapper;
import discriminant.GetDiscriminantResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiscriminantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void withDiscriminantGreaterThanZero() throws Exception {
        GetDiscriminantResponse response = new GetDiscriminantResponse();
        response.setFormula("1.0x^2+4.0x+2.0=0");
        response.setD("8.0");
        response.setX1("-3.414213562373095");
        response.setX2("-0.5857864376269049");

        String expectedValue = objectMapper.writeValueAsString(response);

        mockMvc.perform(get("/api/calc/")
                        .param("a", "1")
                        .param("b", "4")
                        .param("c", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedValue));
    }

    @Test
    public void withDiscriminantIsZero() throws Exception {
        GetDiscriminantResponse response = new GetDiscriminantResponse();
        response.setFormula("1.0x^2+4.0x+4.0=0");
        response.setD("0.0");
        response.setX1("-2.0");

        String expectedValue = objectMapper.writeValueAsString(response);

        mockMvc.perform(get("/api/calc/")
                        .param("a", "1")
                        .param("b", "4")
                        .param("c", "4"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedValue));
    }

    @Test
    public void withDiscriminantLowerThanZero() throws Exception {
        mockMvc.perform(get("/api/calc/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("a", "1")
                        .param("b", "4")
                        .param("c", "40"))
                .andExpect(status().is5xxServerError())
                .andDo(result -> assertTrue(result.getResolvedException() instanceof DiscriminantException));
    }
}
