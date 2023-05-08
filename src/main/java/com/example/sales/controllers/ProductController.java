package com.example.sales.controllers;

import com.example.sales.entity.Product;
import com.example.sales.entity.ProductDetails;
import com.example.sales.service.dao.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductInterface productInterface;

    @Autowired
    public void setProductInterface(final ProductInterface productInterface) {
        this.productInterface = productInterface;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productInterface.getAllProducts();
    }

    @GetMapping("/products/{productId}/details")
    public ProductDetails getProductDetails(@PathVariable Long productId, @RequestParam(required = false) Long clientId) {
        return productInterface.getProductDetails(productId, clientId);
    }

    @GetMapping("/total_product_sum")
    public ResponseEntity<Double> calculatePriceProduct(@RequestParam Long productId, @RequestParam int quantity) {
        Double totalPrice = productInterface.calculateTotalPrice(productId, quantity);
        if (totalPrice == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping("/{clientId}/sum")
    public Double getTotalPrice(@PathVariable Long clientId) {
        return productInterface.getTotalPrice(clientId);
    }
}
