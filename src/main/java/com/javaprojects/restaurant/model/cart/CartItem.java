package com.javaprojects.restaurant.model.cart;

import com.javaprojects.restaurant.model.restaurant.Product;
import lombok.Data;

@Data
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
