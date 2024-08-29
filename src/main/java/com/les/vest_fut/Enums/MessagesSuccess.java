package com.les.vest_fut.Enums;

import lombok.Getter;

@Getter
public enum MessagesSuccess {

    CLIENT_REGISTERED("Cadastro realizado com sucesso! Faça login para continuar."),
    CLIENT_UPDATED("Cadastro atualizado com sucesso!"),
    PASSWORD_UPDATED("Senha atualizada com sucesso!");

    private final String message;

    MessagesSuccess(String message) {
        this.message = message;
    }

}
