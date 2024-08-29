package com.les.vest_fut.Enums;

import lombok.Getter;

@Getter
public enum MessagesExceptions {

    CPF_ALREADY_EXISTS("CPF já está em uso, tente outro"),
    EMAIL_ALREADY_EXISTS("Email já está em uso, tente outro"),
    CLIENT_NOT_FOUND("Cliente não encontrado"),
    CURRENT_PASSWORD_INVALID("Senha atual inválida"),
    CARD_NOT_FOUND("Cartão não encontrado"),
    CARD_REQUIRED("É necessário ter ao menos um cartão cadastrado");

    private final String message;

    MessagesExceptions(String message) {
        this.message = message;
    }

}
