package com.javaprojects.restaurant.controller;

import com.javaprojects.restaurant.model.Cart;
import com.javaprojects.restaurant.model.Order;
import com.javaprojects.restaurant.model.OrderItem;
import com.javaprojects.restaurant.repository.OrderRepository;
import com.javaprojects.restaurant.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CartService cartService;
    private final OrderRepository orderRepository;

    public CheckoutController(CartService cartService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public String checkout(HttpSession session) {
        Cart cart = cartService.getCart(session);
        if(cart.getItems().isEmpty()) {
            return "Cart is empty";
        }

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(item -> {
                    OrderItem orderItem = new OrderItem(item.getProduct(), item.getQuantity());
                    orderItem.setOrder(null);
                    return orderItem;
                })
                .collect(Collectors.toList());

        double totalPrice = cart.getTotalPrice();

        Order order = new Order(totalPrice, orderItems);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        orderRepository.save(order);

        cartService.clearCart(session);

        return "Order placed successfully, Total: " + totalPrice + "$";
    }
}
