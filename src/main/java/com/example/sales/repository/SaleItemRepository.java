package com.example.sales.repository;

import com.example.sales.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    @Query(value = "SELECT * FROM sale_items WHERE client_id = :clientId", nativeQuery = true)
    List<SaleItem> getSales(Long clientId);
}
