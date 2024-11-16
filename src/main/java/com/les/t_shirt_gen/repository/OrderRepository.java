package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.order.Order;
import com.les.t_shirt_gen.model.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(UserEntity user);

    Optional<Order> findByIdAndUser(Long id, UserEntity user);

    List<Order> findByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
