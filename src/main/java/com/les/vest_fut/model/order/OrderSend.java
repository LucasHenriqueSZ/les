package com.les.vest_fut.model.order;

import com.les.vest_fut.model.users.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders_sends")
public class OrderSend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ors_ord_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ors_adr_id", nullable = false)
    private Address address;

    @Column(name = "ors_init_date_pred", nullable = false)
    private LocalDate initDatePrediction;

    @Column(name = "ors_init_date_real")
    private LocalDate initDateReal;

    @Column(name = "ors_end_date_pred", nullable = false)
    private LocalDate endDatePrediction;

    @Column(name = "ors_end_date_real")
    private LocalDate endDateReal;

    @Column(name = "ors_frete_price", nullable = false)
    private BigDecimal fretePrice;
}