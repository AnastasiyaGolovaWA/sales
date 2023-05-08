package com.example.sales.entity;

import lombok.Data;

import java.util.Map;

@Data
public class ProductDetails {
    private String description;
    private Double averageRating;
    private Map<Integer, Long> ratingDistribution;
    private Double clientRating;

    public ProductDetails(String description, Double averageRating, Map<Integer, Long> ratingDistribution, Double clientRating) {
        this.description = description;
        this.averageRating = averageRating;
        this.ratingDistribution = ratingDistribution;
        this.clientRating = clientRating;
    }
}