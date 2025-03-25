package com.javaprojects.restaurant.controller;

import com.javaprojects.restaurant.model.Category;
import com.javaprojects.restaurant.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }

    @GetMapping("/{name}")
    public Category findByName(@PathVariable String name) {
        return categoryService.findByName(name);
    }
}
