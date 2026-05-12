package com.ironhack.quotesapi.dtos.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteUpdateRequest {

    @Size(min = 1, max = 255, message = "Author must not be empty")
    private String author;

    @Size(min = 5, max = 500, message = "Text must be between 5 and 500 characters")
    private String text;

    @Size(min = 1, max = 255, message = "Category must not be empty")
    private String category;

    public QuoteUpdateRequest(String author, String text, String category) {
        this.author = author;
        this.text = text;
        this.category = category;
    }
}