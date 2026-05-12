package com.ironhack.quotesapi.services;

import com.ironhack.quotesapi.entity.Quote;
import com.ironhack.quotesapi.repositories.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {
    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository){
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> getAllQuotes(){
        return quoteRepository.findAll();
    }

    public Quote getQuoteById(Long id){
        return quoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldn't find quote with id: "+ id));
    }

    public Quote createQuote(Quote quote){
        return quoteRepository.save(quote);
    }

    public Quote updateQuote(Long id,Quote updatedQuote){
        Quote existing = getQuoteById(id);
        existing.setAuthor(updatedQuote.getAuthor());
        existing.setText(updatedQuote.getText());
        existing.setCategory(updatedQuote.getCategory());

        return quoteRepository.save(existing);
    }

    public void deleteQuote(Long id){
        quoteRepository.deleteById(id);
    }
}
