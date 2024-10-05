package com.les.vest_fut.repository;

import com.les.vest_fut.model.order.Order;
import com.les.vest_fut.model.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(UserEntity user);

    Optional<Order> findByIdAndUser(Long id, UserEntity user);
}
