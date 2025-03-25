package com.javaprojects.restaurant.repository;

import com.javaprojects.restaurant.model.Category;
import com.javaprojects.restaurant.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryName(String categoryName);
}
