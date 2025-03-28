package com.javaprojects.restaurant.model.cart;

import com.javaprojects.restaurant.model.restaurant.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        for(CartItem item : items) {
            if(item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(Long productId) {
        items.removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public void clear(){
        items.clear();
    }
}
