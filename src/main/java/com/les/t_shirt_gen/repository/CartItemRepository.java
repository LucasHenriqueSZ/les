package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}