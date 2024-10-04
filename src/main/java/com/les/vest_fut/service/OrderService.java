package com.les.vest_fut.service;

import com.les.vest_fut.dto.OrderFormDto;
import com.les.vest_fut.model.order.Order;
import com.les.vest_fut.model.order.OrderStatus;

import java.util.List;

public interface OrderService {
    void createOrder(OrderFormDto orderFormDto);

    List<Order> findAll();

    List<Order> findAllByUser();

    void updateOrderStatus(Long id, OrderStatus status);
}
