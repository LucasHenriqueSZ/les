package com.les.vest_fut.Enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Gender {
    MALE("Masculino"),
    FEMALE("Feminino"),
    OTHER("Outro");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public static List<Gender> getAll() {
        return Arrays.asList(Gender.values());
    }
}
