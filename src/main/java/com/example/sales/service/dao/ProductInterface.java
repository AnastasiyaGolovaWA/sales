package com.example.sales.service.dao;

import com.example.sales.entity.Product;
import com.example.sales.entity.ProductDetails;

import java.util.List;

public interface ProductInterface {

    List<Product> getAllProducts();

    ProductDetails getProductDetails(Long productId, Long clientId);

    Double getTotalPrice(Long clientId);

    Double calculateTotalPrice(Long productId, int quantity);
}
