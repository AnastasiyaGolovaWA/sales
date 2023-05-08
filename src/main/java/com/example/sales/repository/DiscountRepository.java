package com.example.sales.repository;

import com.example.sales.entity.Discount;
import com.example.sales.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Query(value = "SELECT discount FROM discounts WHERE product_id = :productId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Double findDiscountByProductId(Long productId);
}
