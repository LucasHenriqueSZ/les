package com.les.vest_fut.Enums;

import lombok.Getter;

@Getter
public enum MessagesExceptions {

    CPF_ALREADY_EXISTS("CPF já cadastrado"),
    EMAIL_ALREADY_EXISTS("Email já cadastrado");

    private final String message;

    MessagesExceptions(String message) {
        this.message = message;
    }

}
