package com.ironhack.quotesapi;

import com.ironhack.quotesapi.repositories.QuoteRepository;
import com.ironhack.quotesapi.entity.Quote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class QuoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        quoteRepository.deleteAll();
    }

    @Test
    void shouldCreateQuote() throws Exception {
        String body = """
                {
                  "author": "Marcus Aurelius",
                  "text": "The impediment to action advances action.",
                  "category": "philosophy"
                }
                """;

        mockMvc.perform(post("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.author").value("Marcus Aurelius"))
                .andExpect(jsonPath("$.text")
                        .value("The impediment to action advances action."))
                .andExpect(jsonPath("$.category").value("philosophy"))
                .andExpect(jsonPath("$.created_at").exists());
    }

    @Test
    void shouldGetQuoteById() throws Exception {
        Quote saved = quoteRepository.save(new Quote("Seneca", "We suffer more in imagination than in reality.", "stoicism"));

        mockMvc.perform(get("/api/quotes/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.author").value("Seneca"));
    }

    @Test
    void shouldReturn404WhenQuoteNotFound() throws Exception {
        mockMvc.perform(get("/api/quotes/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
