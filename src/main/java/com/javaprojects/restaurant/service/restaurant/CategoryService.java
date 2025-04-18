package com.javaprojects.restaurant.service.restaurant;

import com.javaprojects.restaurant.model.restaurant.Category;
import com.javaprojects.restaurant.repository.restaurant.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

}
