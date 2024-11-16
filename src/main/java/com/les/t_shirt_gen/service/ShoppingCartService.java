package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.model.cart.Cart;

public interface ShoppingCartService {
    void addItemToCart(Long productId, Integer quantity);

    Cart getShoppingCart();

    void removeItemCart(Long itemCartId);

    void increaseQuantityItem(Long itemCartId);

    void decreaseQuantityItem(Long itemCartId);

    void clearCartWithoutRestocking();
}
