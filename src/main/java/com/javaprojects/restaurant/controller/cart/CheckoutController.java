package com.javaprojects.restaurant.controller.cart;

import com.javaprojects.restaurant.model.cart.Cart;
import com.javaprojects.restaurant.model.order.Order;
import com.javaprojects.restaurant.model.order.OrderItem;
import com.javaprojects.restaurant.model.user.User;
import com.javaprojects.restaurant.repository.order.OrderRepository;
import com.javaprojects.restaurant.service.cart.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "User not authenticated";
        }

        User user = (User) authentication.getPrincipal();

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

        Order order = new Order(totalPrice, orderItems, user);

        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(order);
        }
        orderRepository.save(order);

        cartService.clearCart(session);

        return "Order placed successfully, Total: " + totalPrice + "$";
    }
}
