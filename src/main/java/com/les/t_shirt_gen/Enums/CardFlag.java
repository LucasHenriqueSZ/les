package com.les.t_shirt_gen.Enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum CardFlag {
    MASTERCARD("Mastercard"),
    VISA("Visa"),
    ELO("Elo"),
    AMERICAN_EXPRESS("American Express"),
    HIPERCARD("Hipercard");

    private final String name;

    CardFlag(String name) {
        this.name = name;
    }

    public static List<CardFlag> getAll() {
        return Arrays.asList(CardFlag.values());
    }
}
