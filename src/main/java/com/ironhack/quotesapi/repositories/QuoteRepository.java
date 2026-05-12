package com.ironhack.quotesapi.repositories;

import com.ironhack.quotesapi.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository <Quote,Long> {
    boolean existsByAuthorAndText(String author, String text);
}

