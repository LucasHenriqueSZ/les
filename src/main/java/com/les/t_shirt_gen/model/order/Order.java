package com.les.t_shirt_gen.model.order;

import com.les.t_shirt_gen.model.exchange.ExchangeRequest;
import com.les.t_shirt_gen.model.payment.Payment;
import com.les.t_shirt_gen.model.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ord_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeRequest> exchangeRequests = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private OrderSend orderSend;

    @Enumerated(EnumType.STRING)
    @Column(name = "ord_status", nullable = false)
    private OrderStatus status;

    @Column(name = "ord_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "ord_updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "ord_total_amount", nullable = false)
    private BigDecimal totalAmount;

    public String getFormattedPrice() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(this.totalAmount);
    }

    public String getFormattedTotalPriceItems() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(this.getTotalPriceItems());
    }

    public BigDecimal getTotalPriceItems(){
        return items.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getFormattedCreatedAt() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", brazilianLocale);
        return this.createdAt.format(formatter);
    }

    public boolean isDelivered() {
        return this.status == OrderStatus.ENTREGUE;
    }
}