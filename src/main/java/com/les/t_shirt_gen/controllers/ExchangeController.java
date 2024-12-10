package com.les.t_shirt_gen.controllers;

import com.les.t_shirt_gen.Enums.MessagesSuccess;
import com.les.t_shirt_gen.controllers.helpers.ExchangeControllerHelper;
import com.les.t_shirt_gen.dto.ExchangeItemDto;
import com.les.t_shirt_gen.dto.ExchangeRequestDto;
import com.les.t_shirt_gen.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/exchange-request")
public class ExchangeController {

    private final ExchangeService exchangeRequestService;

    @PostMapping
    public ModelAndView requestExchange(@ModelAttribute ExchangeRequestDto exchangeRequestDTO, RedirectAttributes attributes) {
        try {
            List<ExchangeItemDto> selectedItems = exchangeRequestDTO.getExchangeItems()
                    .stream()
                    .filter(item -> Boolean.TRUE.equals(item.getSelected()))
                    .toList();

            exchangeRequestService.makeExchangeOrder(
                    exchangeRequestDTO.getOrderId(),
                    selectedItems,
                    exchangeRequestDTO.getReason(),
                    exchangeRequestDTO.getDescription()
            );

            ExchangeControllerHelper.addSuccessMessage(attributes, MessagesSuccess.SUCCESS_EXCHANGE_REQUEST);
            return ExchangeControllerHelper.redirectOrders();

        } catch (Exception e) {
            ExchangeControllerHelper.addErrorMessage(attributes, e.getMessage());
            return ExchangeControllerHelper.redirectOrders();
        }
    }
}
