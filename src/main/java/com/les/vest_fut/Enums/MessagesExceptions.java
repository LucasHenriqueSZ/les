package com.les.vest_fut.Enums;

import lombok.Getter;

@Getter
public enum MessagesExceptions {

    CPF_ALREADY_EXISTS("CPF já está em uso, tente outro"),
    EMAIL_ALREADY_EXISTS("Email já está em uso, tente outro"),
    CLIENT_NOT_FOUND("Cliente não encontrado");

    private final String message;

    MessagesExceptions(String message) {
        this.message = message;
    }

}
