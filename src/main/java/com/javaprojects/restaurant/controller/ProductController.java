package com.javaprojects.restaurant.controller;

import com.javaprojects.restaurant.model.Category;
import com.javaprojects.restaurant.model.Product;
import com.javaprojects.restaurant.service.CategoryService;
import com.javaprojects.restaurant.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public Product create(@RequestBody Product product) {
        String categoryName = product.getCategory();
        Category category = categoryService.findByName(categoryName);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        product.setCategory(category);
        return productService.create(product);
    }

    @GetMapping("/{categoryName}")
    public List<Product> findAllByCategoryName(@PathVariable String categoryName) {
        return productService.findAllByCategoryName(categoryName);
    }
}
