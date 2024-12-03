package com.les.t_shirt_gen.model.exchange;

import com.les.t_shirt_gen.model.order.OrderItem;
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
@Table(name = "exchange_request_items")
public class ExchangeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eri_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eri_exchange_request_id", nullable = false)
    private ExchangeRequest exchangeRequest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eri_ori_id", nullable = false)
    private OrderItem orderItem;

    @Column(name = "eri_quantity", nullable = false)
    private Integer quantity;

    public BigDecimal getTotalPriceItemsExchange() {
        return orderItem.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
