package com.example.sales.service;

import com.example.sales.entity.*;
import com.example.sales.repository.*;
import com.example.sales.service.dao.ProductInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductInterface {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    RatingsRepository ratingsRepository;

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    SaleItemRepository saleItemRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductDetails getProductDetails(Long productId, Long clientId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        String description = product.getDescription();
        Double averageRating = getProductAverageRating(productId);
        Map<Integer, Long> ratingDistribution = getProductRatingDistribution(productId);
        Double clientRating = getClientProductRating(productId, clientId);
        return new ProductDetails(description, averageRating, ratingDistribution, clientRating);
    }

    public Double getProductAverageRating(Long productId) {
        List<Rating> ratings = ratingsRepository.findByProductId(productId);
        return ratings.stream().mapToDouble(Rating::getRating).average().orElse(0.0);
    }

    public Map<Integer, Long> getProductRatingDistribution(Long productId) {
        List<Rating> ratings = ratingsRepository.findByProductId(productId);
        Map<Integer, Long> distribution = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            final int rating = i;
            long count = ratings.stream().filter(r -> r.getRating() == rating).count();
            distribution.put(rating, count);
        }
        return distribution;
    }

    public Double getClientProductRating(Long productId, Long clientId) {
        Optional<Rating> rating = ratingsRepository.findByProductIdAndClientId(productId, clientId);
        return rating.map(Rating::getRating).orElse(null);
    }

    public Double getTotalPrice(Long clientId) {
        List<SaleItem> sales = saleItemRepository.getSales(clientId);
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            return null;
        }
        double totalPrice = 0.0;
        for (SaleItem sale : sales) {
            totalPrice += applyDiscount((long) (sale.getProduct().getPrice() * sale.getQuantity()), calculateTotalDiscountPercentage(sale.getProduct(), sale.getQuantity(), client));
        }
        return totalPrice;
    }

    private Double calculateTotalDiscountPercentage(Product product, Integer quantity, Client client) {
        Double individualDiscount1 = client.getDiscount1();
        Double individualDiscount2 = client.getDiscount2();
        Double randomDiscount = discountRepository.findDiscountByProductId(product.getId());
        Double totalDiscountPercentage = randomDiscount;
        if (quantity >= 5 && individualDiscount2 != null && individualDiscount2 > 0) {
            totalDiscountPercentage += individualDiscount2;
        } else {
            totalDiscountPercentage += individualDiscount1;
        }
        if (totalDiscountPercentage > 18) {
            totalDiscountPercentage = Double.valueOf(18);
        }
        return totalDiscountPercentage;
    }

    private Double applyDiscount(Long priceInCents, Double totalDiscountPercentage) {
        Double discountedPrice = priceInCents * (1 - totalDiscountPercentage / 100);
        return discountedPrice;
    }

    public Double calculateTotalPrice(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return null;
        }
        Double discount = discountRepository.findDiscountByProductId(productId);
        if (discount == null) {
            return null;
        }
        double price = (product.getPrice() * quantity / 100) * (100 - discount);
        return applyDiscount((long) price, (double) 0);
    }
}
