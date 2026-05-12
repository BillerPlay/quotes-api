package com.ironhack.quotesapi.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteRequest {

    @NotBlank(message = "Author is required")
    private String author;

    @NotBlank(message = "Text is required")
    @Size(min = 5, max = 500, message = "Text must be between 5 and 500 characters")
    private String text;

    @NotBlank(message = "Category is required")
    private String category;

    public QuoteRequest(String author, String text, String category) {
        this.author = author;
        this.text = text;
        this.category = category;
    }
}