package com.les.t_shirt_gen.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.les.t_shirt_gen.model.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cupons")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cup_id")
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "cup_code", length = 20, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "cup_type", nullable = false)
    private CuponType type;

    @DecimalMin("0.0")
    @DecimalMax("100.0")
    @Column(name = "cup_discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @DecimalMin("0.0")
    @Column(name = "cup_amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "usr_id", nullable = false)
    private UserEntity user;

    @Column(name = "cup_used", nullable = false)
    private boolean used;

    @JsonIgnore
    public String getFormattedAmount() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(amount);
    }
}
