package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.dto.ExchangeItemDto;
import com.les.t_shirt_gen.model.exchange.ExchangeRequest;
import com.les.t_shirt_gen.model.exchange.ExchangeStatus;

import java.util.List;
import java.util.Optional;

public interface ExchangeService {
    void makeExchangeOrder(Long orderId, List<ExchangeItemDto> selectedItems, String reason, String description);

    List<ExchangeRequest> findAll();

    Optional<ExchangeRequest> findById(Long id);

    void approveExchangeRequest(Long id, boolean returnToStock, String observations);

    void rejectExchangeRequest(Long id, String observations);

    void updateExchangeRequestStatus(Long id, ExchangeStatus status);
}
