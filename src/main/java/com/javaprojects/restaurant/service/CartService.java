package com.javaprojects.restaurant.service;

import com.javaprojects.restaurant.model.Cart;
import com.javaprojects.restaurant.model.CartItem;
import com.javaprojects.restaurant.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addItem(HttpSession session, Product product, int quantity) {
        Cart cart = getCart(session);
        cart.addItem(product, quantity);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeItem(HttpSession session, Long productId) {
        Cart cart = getCart(session);
        cart.removeItem(productId);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void clearCart(HttpSession session) {
        Cart cart = getCart(session);
        cart.clear();
        session.setAttribute(CART_SESSION_KEY, cart);
    }
}
