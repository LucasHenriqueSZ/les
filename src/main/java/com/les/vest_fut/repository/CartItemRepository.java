package com.les.vest_fut.repository;

import com.les.vest_fut.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
