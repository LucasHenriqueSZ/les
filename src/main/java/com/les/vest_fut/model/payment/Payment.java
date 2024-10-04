package com.les.vest_fut.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.les.vest_fut.model.order.Order;
import com.les.vest_fut.model.users.Card;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cupon_id")
    private Cupon cupon;

    public String getFormattedAmount() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(amount);
    }
}