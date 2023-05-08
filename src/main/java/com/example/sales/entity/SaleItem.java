package com.example.sales.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sale_items")
@Data
public class SaleItem {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;
}

