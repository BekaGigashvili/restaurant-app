package com.javaprojects.restaurant.controller.restaurant;

import com.javaprojects.restaurant.model.restaurant.Category;
import com.javaprojects.restaurant.service.restaurant.CategoryService;
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
