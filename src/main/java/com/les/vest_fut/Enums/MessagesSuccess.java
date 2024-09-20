package com.les.vest_fut.Enums;

import lombok.Getter;

@Getter
public enum MessagesSuccess {

    CLIENT_REGISTERED("Cadastro realizado com sucesso! Faça login para continuar."),
    CLIENT_UPDATED("Cadastro atualizado com sucesso!"),
    PASSWORD_UPDATED("Senha atualizada com sucesso!"),
    CARD_REMOVED("Cartão removido com sucesso!"),
    CARD_UPDATED("Cartão atualizado com sucesso!"),
    CARD_REGISTERED("Cartão cadastrado com sucesso!"),
    ADDRESS_REGISTERED("Endereço cadastrado com sucesso!"),
    ADDRESS_UPDATED("Endereço atualizado com sucesso!"),
    ADDRESS_REMOVED("Endereço removido com sucesso!"),
    CLIENT_STATUS_ALTERED("Status do cliente alterado com sucesso!"),
    CART_ADD_PRODUCT("Produto Adicionado ao carrinho com sucesso!"),
    CART_REMOVE_ITEM("Item removido do carrinho com sucesso!");

    private final String message;

    MessagesSuccess(String message) {
        this.message = message;
    }

}
