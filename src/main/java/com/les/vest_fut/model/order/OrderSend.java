package com.les.vest_fut.model.order;

import com.les.vest_fut.model.users.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    @ManyToOne(fetch = FetchType.EAGER)
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

    public String getFormattedPrice() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(fretePrice);
    }

    public String getFormattedInitDatePrediction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
        return this.initDatePrediction.format(formatter);
    }

    public String getFormattedInitDateReal() {
        if (this.initDateReal != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
            return this.initDateReal.format(formatter);
        } else {
            return "A ser enviado";
        }
    }

    public String getFormattedEndDatePrediction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
        return this.endDatePrediction.format(formatter);
    }

    public String getFormattedEndDateReal() {
        if (this.endDateReal != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));
            return this.endDateReal.format(formatter);
        } else {
            return "Não entregue";
        }
    }
}