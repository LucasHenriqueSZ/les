package com.les.vest_fut.repository;

import com.les.vest_fut.model.cart.Cart;
import com.les.vest_fut.model.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(UserEntity user);
}
