package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.dto.OrderFormDto;
import com.les.t_shirt_gen.model.order.Order;
import com.les.t_shirt_gen.model.order.OrderStatus;

import java.util.List;

public interface OrderService {
    void createOrder(OrderFormDto orderFormDto);

    List<Order> findAll();

    List<Order> findAllByUser();

    void updateOrderStatus(Long id, OrderStatus status);

    Order findById(Long orderId);
}
