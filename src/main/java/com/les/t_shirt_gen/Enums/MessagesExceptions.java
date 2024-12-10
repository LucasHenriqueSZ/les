package com.les.t_shirt_gen.Enums;

import lombok.Getter;

@Getter
public enum MessagesExceptions {

    CPF_ALREADY_EXISTS("CPF já está em uso, tente outro"),
    EMAIL_ALREADY_EXISTS("Email já está em uso, tente outro"),
    CLIENT_NOT_FOUND("Cliente não encontrado"),
    CURRENT_PASSWORD_INVALID("Senha atual inválida"),
    CARD_NOT_FOUND("Cartão não encontrado"),
    CARD_REQUIRED("É necessário ter ao menos um cartão cadastrado"),
    CARD_ALREADY_EXISTS("Cartão já cadastrado"),
    ADDRESS_ALREADY_EXISTS("Endereço já cadastrdo"),
    ADDRESS_NOT_FOUND("Endereço não encontrado"),
    ADDRESS_REQUIRED("É necessário ter ao menos um endereço cadastrado"),
    SHOPPING_CART_NOT_EXIST("Carrinho não encontrado para o usuário logado"),
    SELECT_DELIVERY_ADDRESS("Selecione o endereço de entrega"),
    INVALID_TOTAL_PAID("O valor pago não confere com o total do carrinho e frete"),
    COUPON_NOT_FOUND("Cupom não encontrado"),
    COUPON_ALREADY_USED("Cupom já foi utilizado"),
    ORDER_NOT_FOUND("Status pedido invalido");

    private final String message;

    MessagesExceptions(String message) {
        this.message = message;
    }

}
