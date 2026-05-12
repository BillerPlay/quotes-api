package com.ironhack.quotesapi.dtos.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuoteResponse {

    private Long id;
    private String author;
    private String text;
    private String category;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public QuoteResponse() {
    }

    public QuoteResponse(Long id, String author, String text, String category, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.category = category;
        this.createdAt = createdAt;
    }

}