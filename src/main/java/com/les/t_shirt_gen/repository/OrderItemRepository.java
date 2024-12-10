package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
