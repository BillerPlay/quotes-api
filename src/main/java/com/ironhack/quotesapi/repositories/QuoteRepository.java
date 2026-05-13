package com.ironhack.quotesapi.repositories;

import com.ironhack.quotesapi.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository <Quote,Long> {
    @Query(value = "SELECT * FROM quote q WHERE " +
            "LOWER(q.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(q.text) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(q.category) LIKE LOWER(CONCAT('%', :keyword, '%'))",
            nativeQuery = true)
    List<Quote> searchByKeywordNative(@Param("keyword") String keyword);

    boolean existsByAuthorAndText(String author, String text);
}

