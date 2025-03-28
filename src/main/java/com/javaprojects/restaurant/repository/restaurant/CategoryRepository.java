package com.javaprojects.restaurant.repository.restaurant;

import com.javaprojects.restaurant.model.restaurant.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
