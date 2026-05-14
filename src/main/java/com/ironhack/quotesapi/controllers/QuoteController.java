package com.ironhack.quotesapi.controllers;

import com.ironhack.quotesapi.dtos.request.QuoteRequest;
import com.ironhack.quotesapi.dtos.request.QuoteUpdateRequest;
import com.ironhack.quotesapi.dtos.response.QuoteResponse;
import com.ironhack.quotesapi.services.QuoteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final QuoteService quoteService;

    public  QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public Page<QuoteResponse> getAll(
            @RequestParam(name = "search" ,required = false) String search,
            Pageable pageable) {
        return quoteService.getQuotes(search,pageable);
    }

    @GetMapping("/{id}")
    public QuoteResponse getById(@PathVariable Long id) {
        return quoteService.getQuoteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuoteResponse create(@Valid @RequestBody QuoteRequest request) {
        return quoteService.createQuote(request);
    }

    @PutMapping ("/{id}")
    public QuoteResponse update(@PathVariable Long id,@Valid @RequestBody QuoteUpdateRequest request) {
        return quoteService.updateQuote(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        quoteService.deleteQuote(id);
    }
}
