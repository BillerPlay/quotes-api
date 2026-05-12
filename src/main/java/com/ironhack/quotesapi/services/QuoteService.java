package com.ironhack.quotesapi.services;

import com.ironhack.quotesapi.dtos.request.QuoteRequest;
import com.ironhack.quotesapi.dtos.request.QuoteUpdateRequest;
import com.ironhack.quotesapi.dtos.response.QuoteResponse;
import com.ironhack.quotesapi.entity.Quote;
import com.ironhack.quotesapi.exceptions.ConflictException;
import com.ironhack.quotesapi.exceptions.ResourceNotFoundException;
import com.ironhack.quotesapi.repositories.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
    }

    public List<QuoteResponse> getAllQuotes(){
        return quoteRepository.findAll().stream().map(quote -> {
            QuoteResponse response = new QuoteResponse();
            response.setId(quote.getId());
            response.setAuthor(quote.getAuthor());
            response.setText(quote.getText());
            response.setCategory(quote.getCategory());
            response.setCreatedAt(quote.getCreatedAt());
            return response;
        }).collect(Collectors.toList());
    }

    public QuoteResponse getQuoteById(Long id){
        Quote quote = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find quote with id: "+ id));

        QuoteResponse response = new QuoteResponse();
        response.setId(quote.getId());
        response.setAuthor(quote.getAuthor());
        response.setText(quote.getText());
        response.setCategory(quote.getCategory());
        response.setCreatedAt(quote.getCreatedAt());

        return response;
    }

    public QuoteResponse createQuote(QuoteRequest request){
        if(quoteRepository.existsByAuthorAndText(request.getAuthor(), request.getText())){
            throw new ConflictException("This author already has quote like this!");
        }

        Quote quote = new Quote();
        quote.setAuthor(request.getAuthor());
        quote.setText(request.getText());
        quote.setCategory(request.getCategory());

        quoteRepository.save(quote);

        QuoteResponse response = new QuoteResponse();
        response.setId(quote.getId());
        response.setAuthor(quote.getAuthor());
        response.setText(quote.getText());
        response.setCategory(quote.getCategory());
        response.setCreatedAt(quote.getCreatedAt());

        return response;
    }

    public QuoteResponse updateQuote(Long id, QuoteUpdateRequest request){
        if(quoteRepository.existsByAuthorAndText(request.getAuthor(), request.getText())){
            throw new ConflictException("This author already has quote like this!");
        }

        Quote existing = quoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find quote with id: "+ id));

        existing.setAuthor(request.getAuthor());
        existing.setText(request.getText());
        existing.setCategory(request.getCategory());

        quoteRepository.save(existing);

        QuoteResponse response = new QuoteResponse();
        response.setId(existing.getId());
        response.setAuthor(existing.getAuthor());
        response.setText(existing.getText());
        response.setCategory(existing.getCategory());
        response.setCreatedAt(existing.getCreatedAt());

        return response;
    }

    public void deleteQuote(Long id){
        if (!quoteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Couldn't find quote with id: " + id);
        }

        quoteRepository.deleteById(id);
    }
}
