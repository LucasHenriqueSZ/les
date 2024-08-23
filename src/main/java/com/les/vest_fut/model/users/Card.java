package com.les.vest_fut.model.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.les.vest_fut.Enums.CardFlag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crd_id")
    private Long id;

    @NotBlank(message = "{NotBlank.card.cardNumber}")
    @Size(min = 16, max = 16, message = "{Size.card.cardNumber}")
    @Column(name = "crd_number", nullable = false)
    private String cardNumber;

    @NotBlank(message = "{NotBlank.card.holderName}")
    @Size(max = 100, message = "{Size.card.holderName}")
    @Column(name = "crd_holder_name", nullable = false)
    private String holderName;

    @NotBlank(message = "{NotBlank.card.expiryDate}")
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})", message = "{Pattern.card.expiryDate}")
    @Column(name = "crd_expiry_date", nullable = false)
    private String expiryDate;

    @NotBlank(message = "{NotBlank.card.cvv}")
    @Size(min = 3, max = 4, message = "{Size.card.cvv}")
    @Column(name = "crd_cvv", nullable = false)
    private String cvv;

    @NotNull(message = "{NotNull.card.flag}")
    @Enumerated(EnumType.STRING)
    @Column(name = "crd_flag", nullable = false)
    private CardFlag flag;

    public Card(String cardNumber, String holderName, String expiryDate, String cvv) {
        setCardNumber(cardNumber);
        setHolderName(holderName);
        setExpiryDate(expiryDate);
        setCvv(cvv);
    }

    public Card() {
    }

    @JsonProperty("flagName")
    public String getFlagName() {
        return flag != null ? flag.getName() : null;
    }
}
