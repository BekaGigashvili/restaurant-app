package com.javaprojects.restaurant.repository.restaurant;

import com.javaprojects.restaurant.model.restaurant.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryName(String categoryName);
}
