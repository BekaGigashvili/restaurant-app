package com.javaprojects.restaurant.controller;

import com.javaprojects.restaurant.model.Cart;
import com.javaprojects.restaurant.model.CartItem;
import com.javaprojects.restaurant.model.Product;
import com.javaprojects.restaurant.repository.ProductRepository;
import com.javaprojects.restaurant.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;

    public CartController(CartService cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public Cart getCart(HttpSession session){
        return cartService.getCart(session);
    }


    @PostMapping("/add")
    public String addItem(@RequestParam Long productId,
                          @RequestParam int quantity,
                          HttpSession session){
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new RuntimeException("Product not found"));
        cartService.addItem(session, product, quantity);
        return "Product added";
    }

    @DeleteMapping("/delete/{productId}")
    public String removeItem(@PathVariable Long productId,
                             HttpSession session){
        cartService.removeItem(session, productId);
        return "Product removed";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session){
        cartService.clearCart(session);
        return "Cart cleared";
    }
}
