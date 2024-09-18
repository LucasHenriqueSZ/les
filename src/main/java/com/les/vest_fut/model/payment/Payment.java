package com.les.vest_fut.model.payment;

import com.les.vest_fut.model.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pym_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "pym_method", nullable = false)
    private PaymentMethod method;

    @Column(name = "pym_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "pym_details")
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id", nullable = false)
    private Order order;
}