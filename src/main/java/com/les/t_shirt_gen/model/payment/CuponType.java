package com.les.t_shirt_gen.model.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CuponType {
    PERCENTAGE("porcentagem"),
    FIXED_AMOUNT("valor fixo");

    private final String name;
}