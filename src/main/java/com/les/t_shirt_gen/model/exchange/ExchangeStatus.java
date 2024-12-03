package com.les.t_shirt_gen.model.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
@Getter
public enum ExchangeStatus {
    REQUESTED("Solicitada"),
    UNDER_REVIEW("Em análise"),
    EVALUATING_PRODUCT("Avaliando Produto"),
    APPROVED("Aprovada"),
    COMPLETED("Concluída"),
    REJECTED("Rejeitada"),
    EXCHANGE_RECEIVED("Troca Recebida");

    private final String name;

    public static List<ExchangeStatus> getEditableStatuses() {
        return List.of(UNDER_REVIEW, EXCHANGE_RECEIVED, EVALUATING_PRODUCT, APPROVED);
    }
}
