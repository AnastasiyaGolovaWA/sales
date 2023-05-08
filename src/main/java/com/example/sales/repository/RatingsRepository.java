package com.example.sales.repository;

import com.example.sales.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingsRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT * FROM ratings WHERE product_id = :productId", nativeQuery = true)
    List<Rating> findByProductId(Long productId);

    @Query(value = "SELECT * FROM ratings WHERE product_id = :productId AND client_id = :clientId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Rating> findByProductIdAndClientId(Long productId, Long clientId);
}
