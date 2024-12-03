package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.dto.ExchangeItemDto;
import com.les.t_shirt_gen.model.exchange.ExchangeItem;
import com.les.t_shirt_gen.model.exchange.ExchangeRequest;
import com.les.t_shirt_gen.model.exchange.ExchangeStatus;
import com.les.t_shirt_gen.model.order.Order;
import com.les.t_shirt_gen.model.order.OrderItem;
import com.les.t_shirt_gen.model.order.OrderStatus;
import com.les.t_shirt_gen.model.product.Product;
import com.les.t_shirt_gen.repository.ExchangeRequestRepository;
import com.les.t_shirt_gen.repository.OrderItemRepository;
import com.les.t_shirt_gen.repository.OrderRepository;
import com.les.t_shirt_gen.repository.ProductRepository;
import com.les.t_shirt_gen.service.CuponService;
import com.les.t_shirt_gen.service.ExchangeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final OrderRepository orderRepository;
    private final ExchangeRequestRepository exchangeRequestRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CuponService cuponService;

    @Override
    @Transactional
    public void makeExchangeOrder(Long orderId, List<ExchangeItemDto> selectedItems, String reason, String description) {
        Order order = getOrderById(orderId);

        if (selectedItems.isEmpty()) {
            throw new IllegalArgumentException("Nenhum item de troca foi selecionado");
        }

        List<OrderItem> validOrderItems = validateAndRetrieveOrderItems(selectedItems);

        String combinedReasonDescription = combineReasonAndDescription(reason, description);

        ExchangeRequest exchangeRequest = createExchangeRequest(order, combinedReasonDescription);

        List<ExchangeItem> exchangeItems = createExchangeItems(exchangeRequest, validOrderItems, selectedItems);
        exchangeRequest.setExchangeItems(exchangeItems);

        order.setStatus(OrderStatus.EXCHANGE_REQUESTED);

        saveExchangeRequestAndOrder(exchangeRequest, order);
    }

    @Override
    public List<ExchangeRequest> findAll() {
        return exchangeRequestRepository.findAll();
    }

    @Override
    public Optional<ExchangeRequest> findById(Long id) {
        return exchangeRequestRepository.findById(id);
    }

    @Override
    @Transactional
    public void approveExchangeRequest(Long id, boolean returnToStock, String observations) {

        ExchangeRequest exchangeRequest = exchangeRequestRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Solicitação de troca não encontrada para o ID: " + id));

        if (returnToStock){
            returnItemsToStock(exchangeRequest);
        }

        cuponService.generateNewCuponToUser(exchangeRequest.getUser(), exchangeRequest.getTotalAmountItensExchange());

        exchangeRequest.setStatus(ExchangeStatus.COMPLETED);
        exchangeRequest.setObservations(observations);
        exchangeRequest.setCompletedAt(LocalDateTime.now());
        exchangeRequest.getOrder().setStatus(OrderStatus.EXCHANGE_COMPLETED);

        exchangeRequestRepository.save(exchangeRequest);

    }

    private void returnItemsToStock(ExchangeRequest exchangeRequest) {
        List<ExchangeItem> exchangeItems = exchangeRequest.getExchangeItems();
        for (ExchangeItem exchangeItem : exchangeItems) {
            OrderItem orderItem = exchangeItem.getOrderItem();
            Product product = orderItem.getProduct();

            product.setStock(product.getStock() + exchangeItem.getQuantity());

            productRepository.save(product);
        }
    }

    @Override
    @Transactional
    public void rejectExchangeRequest(Long id, String observations) {
        ExchangeRequest exchangeRequest = exchangeRequestRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Solicitação de troca não encontrada para o ID: " + id));

        exchangeRequest.setStatus(ExchangeStatus.REJECTED);
        exchangeRequest.setObservations(observations);
        exchangeRequest.setCompletedAt(LocalDateTime.now());
        exchangeRequest.getOrder().setStatus(OrderStatus.EXCHANGE_COMPLETED);

        exchangeRequestRepository.save(exchangeRequest);
    }

    @Override
    public void updateExchangeRequestStatus(Long id, ExchangeStatus status) {
        ExchangeRequest exchangeRequest = exchangeRequestRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Solicitação de troca não encontrada para o ID: " + id));
        if (exchangeRequest.getStatus() == ExchangeStatus.COMPLETED || exchangeRequest.getStatus() == ExchangeStatus.REJECTED) {
            throw new IllegalArgumentException("Solicitação de troca ja finalizada");
        }
        exchangeRequest.setStatus(status);
        orderRepository.save(exchangeRequest.getOrder());
    }

    private String combineReasonAndDescription(String reason, String description) {
        if (description == null || description.trim().isEmpty()) {
            return reason;
        }
        return reason + " | Descrição adicional: " + description;
    }


    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado para o ID: " + orderId));
    }

    private List<OrderItem> validateAndRetrieveOrderItems(List<ExchangeItemDto> selectedItems) {
        return selectedItems.stream()
                .map(itemDto -> {
                    OrderItem orderItem = orderItemRepository.findById(itemDto.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException("Item não encontrado para o ID: " + itemDto.getItemId()));

                    if (itemDto.getQuantity() <= 0 || itemDto.getQuantity() > orderItem.getQuantity()) {
                        throw new IllegalArgumentException("Quantidade inválida para o item: " + itemDto.getItemId());
                    }

                    return orderItem;
                })
                .toList();
    }

    private ExchangeRequest createExchangeRequest(Order order, String reason) {
        ExchangeRequest exchangeRequest = new ExchangeRequest();
        exchangeRequest.setOrder(order);
        exchangeRequest.setUser(order.getUser());
        exchangeRequest.setReason(reason);
        exchangeRequest.setStatus(ExchangeStatus.REQUESTED);
        exchangeRequest.setCreatedAt(LocalDateTime.now());
        return exchangeRequest;
    }

    private List<ExchangeItem> createExchangeItems(ExchangeRequest exchangeRequest, List<OrderItem> validOrderItems, List<ExchangeItemDto> selectedItems) {
        List<ExchangeItem> exchangeItems = new ArrayList<>();
        for (ExchangeItemDto itemDto : selectedItems) {
            ExchangeItem exchangeItem = new ExchangeItem();
            exchangeItem.setExchangeRequest(exchangeRequest);
            exchangeItem.setOrderItem(
                    validOrderItems.stream()
                            .filter(orderItem -> orderItem.getId().equals(itemDto.getItemId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Item não encontrado na lista de validação"))
            );
            exchangeItem.setQuantity(itemDto.getQuantity());
            exchangeItems.add(exchangeItem);
        }
        return exchangeItems;
    }

    private void saveExchangeRequestAndOrder(ExchangeRequest exchangeRequest, Order order) {
        exchangeRequestRepository.save(exchangeRequest);
        orderRepository.save(order);
    }
}
