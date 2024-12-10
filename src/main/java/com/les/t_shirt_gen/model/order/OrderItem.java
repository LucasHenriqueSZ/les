package com.les.t_shirt_gen.model.order;

import com.les.t_shirt_gen.model.product.Product;
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
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ori_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ord_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pdt_id", nullable = false)
    private Product product;

    @Column(name = "ori_quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ori_price", nullable = false)
    private BigDecimal price;


    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public String getFormattedTotalPrice() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(this.getTotalPrice());
    }

    public String getFormattedPrice() {
        Locale brazilianLocale = Locale.forLanguageTag("pt-BR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilianLocale);
        return currencyFormat.format(this.getPrice());
    }
}
