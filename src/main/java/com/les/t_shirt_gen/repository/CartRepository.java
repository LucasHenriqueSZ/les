package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.cart.Cart;
import com.les.t_shirt_gen.model.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(UserEntity user);
}
