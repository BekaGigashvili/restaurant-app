package com.javaprojects.restaurant.controller.cart;

import com.javaprojects.restaurant.model.cart.Cart;
import com.javaprojects.restaurant.model.restaurant.Product;
import com.javaprojects.restaurant.repository.restaurant.ProductRepository;
import com.javaprojects.restaurant.service.cart.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return cartService.getCart(session);
    }


    @PostMapping("/add")
    public String addItem(@RequestParam Long productId,
                          @RequestParam int quantity,
                          HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new RuntimeException("Product not found"));
        cartService.addItem(session, product, quantity);
        return "Product added";
    }

    @DeleteMapping("/delete/{productId}")
    public String removeItem(@PathVariable Long productId,
                             HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        cartService.removeItem(session, productId);
        return "Product removed";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        cartService.clearCart(session);
        return "Cart cleared";
    }
}
