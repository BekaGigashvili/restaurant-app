package com.javaprojects.restaurant.service;

import com.javaprojects.restaurant.model.Category;
import com.javaprojects.restaurant.model.Product;
import com.javaprojects.restaurant.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllByCategoryName(String categoryName) {
        return productRepository.findAllByCategoryName(categoryName);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
