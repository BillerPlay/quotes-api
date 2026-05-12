package com.ironhack.quotesapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String author;

    @Size (min = 5,max = 500)
    private String text;

    @NotBlank
    private String category;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Quote(){
    }

    public Quote(Long id, String author, String text, String category, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.category = category;
        this.createdAt = createdAt;
    }

    public Quote(String author, String text, String category) {
        this.author = author;
        this.text = text;
        this.category = category;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
