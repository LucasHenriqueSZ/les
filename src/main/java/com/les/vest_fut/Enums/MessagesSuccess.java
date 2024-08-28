package com.les.vest_fut.Enums;

import lombok.Getter;

@Getter
public enum MessagesSuccess {

    CLIENT_REGISTERED("Cadastro realizado com sucesso! Faça login para continuar.");

    private final String message;

    MessagesSuccess(String message) {
        this.message = message;
    }

}
