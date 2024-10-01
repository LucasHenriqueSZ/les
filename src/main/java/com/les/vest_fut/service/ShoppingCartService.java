package com.les.vest_fut.service;

import com.les.vest_fut.model.cart.Cart;

public interface ShoppingCartService {
    void addItemToCart(Long productId, Integer quantity);

    Cart getShoppingCart();

    void removeItemCart(Long itemCartId);

    void increaseQuantityItem(Long itemCartId);

    void decreaseQuantityItem(Long itemCartId);

    void clearCartWithoutRestocking();
}
