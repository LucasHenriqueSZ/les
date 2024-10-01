package com.les.vest_fut.model.order;

import com.les.vest_fut.Enums.CardFlag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    EM_PROCESSAMENTO("Em processamento"),
    PAGAMENTO_REALIZADO("Pagamento realizado"),
    PAGAMENTO_REJEITADO("Pagamento rejeitado"),
    EM_TRANSPORTE("Em transporte"),
    ENTREGUE("Entregue");

    private final String name;

    public static List<OrderStatus> getAll() {
        return Arrays.asList(OrderStatus.values());
    }
}
