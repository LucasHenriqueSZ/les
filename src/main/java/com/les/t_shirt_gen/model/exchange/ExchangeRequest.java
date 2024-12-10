package com.les.t_shirt_gen.model.exchange;

import com.les.t_shirt_gen.model.order.Order;
import com.les.t_shirt_gen.model.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
@Table(name = "exchange_requests")
public class ExchangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exr_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exr_order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exr_user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "exchangeRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExchangeItem> exchangeItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "exr_status", nullable = false)
    private ExchangeStatus status;

    @Column(name = "exr_reason", nullable = false)
    private String reason;

    @Column(name = "exr_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "exr_updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "exr_observations")
    private String observations;

    @Column(name = "exr_completed_at")
    private LocalDateTime completedAt;

    public void setOrder(Order order) {
        this.order = order;
        if (!order.getExchangeRequests().contains(this)) {
            order.getExchangeRequests().add(this);
        }
    }

    public String getFormattedCreatedAt() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", brazilianLocale);
        return this.createdAt.format(formatter);
    }

    public BigDecimal getTotalAmountItensExchange() {
        return exchangeItems.stream()
                .map(ExchangeItem::getTotalPriceItemsExchange)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
