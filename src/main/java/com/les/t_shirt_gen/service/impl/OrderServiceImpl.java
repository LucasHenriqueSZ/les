package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.Enums.MessagesExceptions;
import com.les.t_shirt_gen.dto.OrderFormDto;
import com.les.t_shirt_gen.model.cart.Cart;
import com.les.t_shirt_gen.model.order.Order;
import com.les.t_shirt_gen.model.order.OrderItem;
import com.les.t_shirt_gen.model.order.OrderStatus;
import com.les.t_shirt_gen.model.payment.Cupon;
import com.les.t_shirt_gen.model.payment.Payment;
import com.les.t_shirt_gen.model.payment.PaymentMethod;
import com.les.t_shirt_gen.model.users.UserEntity;
import com.les.t_shirt_gen.repository.*;
import com.les.t_shirt_gen.security.SecurityUtil;
import com.les.t_shirt_gen.service.OrderService;
import com.les.t_shirt_gen.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final SecurityUtil securityUtil;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final CuponRepository cuponRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public void createOrder(OrderFormDto orderFormDto) {
        UserEntity user = securityUtil.getUserSession();
        Cart cart = getCartByUser(user);

        validateShippingAddress(orderFormDto);

        BigDecimal cartPrice = cart.calculateTotal();
        BigDecimal totalPrice = calculateTotalPrice(cartPrice, orderFormDto.getOrderSend().getFretePrice());

        BigDecimal totalPaid = calculateTotalPaid(orderFormDto.getPayments());

        validateTotalPaid(totalPaid, totalPrice);

        Order order = buildOrder(orderFormDto, cart, user, totalPrice);

        orderRepository.save(order);
        savePayments(orderFormDto.getPayments(), order);
        useCoupons(orderFormDto.getPayments());

        shoppingCartService.clearCartWithoutRestocking();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findAllByUser() {
        List<Order> orders = orderRepository.findAllByUser(securityUtil.getUserSession());
        if (orders != null && !orders.isEmpty()) {
            orders.sort((o1, o2) -> {
                LocalDateTime date1 = o1.getUpdatedAt() != null ? o1.getUpdatedAt() : o1.getCreatedAt();
                LocalDateTime date2 = o2.getUpdatedAt() != null ? o2.getUpdatedAt() : o2.getCreatedAt();

                return date2.compareTo(date1);
            });
        }
        return orders;
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(MessagesExceptions.ORDER_NOT_FOUND.getMessage()));
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public Order findById(Long orderId) {
        UserEntity user = securityUtil.getUserSession();
        return orderRepository.findByIdAndUser(orderId, user).orElseThrow(() -> new IllegalArgumentException(MessagesExceptions.ORDER_NOT_FOUND.getMessage()));
    }

    private Cart getCartByUser(UserEntity user) {
        return cartRepository.findByUser(user).orElseThrow(
                () -> new IllegalStateException(MessagesExceptions.SHOPPING_CART_NOT_EXIST.getMessage()));
    }

    private void validateShippingAddress(OrderFormDto orderFormDto) {
        if (orderFormDto.getOrderSend().getFretePrice() == null) {
            throw new IllegalArgumentException(MessagesExceptions.SELECT_DELIVERY_ADDRESS.getMessage());
        }
    }

    private BigDecimal calculateTotalPrice(BigDecimal cartPrice, BigDecimal shippingPrice) {
        return cartPrice.add(shippingPrice);
    }

    private BigDecimal calculateTotalPaid(List<Payment> payments) {
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateTotalPaid(BigDecimal totalPaid, BigDecimal totalPrice) {
        if (!isTotalPaidValid(totalPaid, totalPrice)) {
            throw new IllegalArgumentException(MessagesExceptions.INVALID_TOTAL_PAID.getMessage());
        }
    }

    private Order buildOrder(OrderFormDto orderFormDto, Cart cart, UserEntity user, BigDecimal totalPrice) {
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalPrice);
        order.setStatus(OrderStatus.EM_PROCESSAMENTO);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = createOrderItemsFromCart(cart, order);
        order.setItems(orderItems);
        orderFormDto.getOrderSend().setOrder(order);
        order.setOrderSend(orderFormDto.getOrderSend());

        return order;
    }

    private List<OrderItem> createOrderItemsFromCart(Cart cart, Order order) {
        return cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            return orderItem;
        }).collect(Collectors.toList());
    }

    private void savePayments(List<Payment> payments, Order order) {
        for (Payment payment : payments) {
            payment.setOrder(order);
            paymentRepository.save(payment);
        }
    }

    private void useCoupons(List<Payment> payments) {
        for (Payment payment : payments) {
            if (payment.getMethod() == PaymentMethod.CUPOM && payment.getCupon().getId() != null) {
                Cupon cupon = cuponRepository.findById(payment.getCupon().getId())
                        .orElseThrow(() -> new IllegalArgumentException(MessagesExceptions.COUPON_NOT_FOUND.getMessage()));

                if (cupon.isUsed()) {
                    throw new IllegalArgumentException(MessagesExceptions.COUPON_ALREADY_USED.getMessage());
                }

                cupon.setUsed(true);
                cuponRepository.save(cupon);
            }
        }
    }

    private boolean isTotalPaidValid(BigDecimal totalPaid, BigDecimal totalPrice) {
        BigDecimal adjustedTotalPaid = totalPaid.setScale(2, RoundingMode.DOWN);
        BigDecimal adjustedTotalPrice = totalPrice.setScale(2, RoundingMode.DOWN);

        BigDecimal tolerance = new BigDecimal("0.01");
        return adjustedTotalPaid.subtract(adjustedTotalPrice).abs().compareTo(tolerance) <= 0;
    }
}
