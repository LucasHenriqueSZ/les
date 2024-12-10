package com.les.t_shirt_gen.model.product;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdt_id")
    private Long id;

    @Column(name = "pdt_price", nullable = false)
    private BigDecimal price;

    @Column(name = "pdt_description")
    private String description;

    @Column(name = "pdt_name", nullable = false)
    private String name;

    @Column(name = "pdt_size")
    private String size;

    @Column(name = "pdt_stock")
    private Integer stock;

    public String getFormattedPrice() {
        if (price == null) {
            return "";
        }
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(price);
    }
}
