package com.ironhack.quotesapi.controllers;

import com.ironhack.quotesapi.dtos.request.QuoteRequest;
import com.ironhack.quotesapi.dtos.request.QuoteUpdateRequest;
import com.ironhack.quotesapi.dtos.response.QuoteResponse;
import com.ironhack.quotesapi.entity.Quote;
import com.ironhack.quotesapi.repositories.QuoteRepository;
import com.ironhack.quotesapi.services.QuoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    public  QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public List<QuoteResponse> getAll() {
        return quoteService.getAllQuotes();
    }

    @GetMapping("/{id}")
    public QuoteResponse getById(@PathVariable Long id) {
        return quoteService.getQuoteById(id);
    }

    @PostMapping
    public QuoteResponse create(@RequestBody QuoteRequest request) {
        return quoteService.createQuote(request);
    }

    @PutMapping
    public QuoteResponse update(@PathVariable Long id,@Valid @RequestBody QuoteUpdateRequest request) {
        return quoteService.updateQuote(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        quoteService.deleteQuote(id);
    }
}
