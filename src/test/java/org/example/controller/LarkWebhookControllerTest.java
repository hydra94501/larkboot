package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LarkWebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUrlVerification() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("challenge", "test_challenge_value");
        requestBody.put("token", "test_token");
        requestBody.put("type", "url_verification");

        mockMvc.perform(post("/webhook/lark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.challenge").value("test_challenge_value"));
    }

    @Test
    public void testEventCallback() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("schema", "2.0");
        Map<String, Object> header = new HashMap<>();
        header.put("event_id", "evt_123");
        requestBody.put("header", header);
        Map<String, Object> event = new HashMap<>();
        event.put("message", "hello");
        requestBody.put("event", event);

        mockMvc.perform(post("/webhook/lark")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
