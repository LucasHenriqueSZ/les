package com.les.vest_fut.controllers;

import com.les.vest_fut.controllers.helpers.OrderControllerHelper;
import com.les.vest_fut.dto.OrderFormDto;
import com.les.vest_fut.model.order.Order;
import com.les.vest_fut.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/pedidos")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @RequestMapping()
    public ModelAndView orders() {
        List<Order> orders = orderService.findAllByUser();
        return OrderControllerHelper.prepareOrdersUser(orders);
    }

    @PostMapping()
    public ModelAndView newOrder(OrderFormDto orderFormDto, RedirectAttributes attributes) {
        try {
            orderService.createOrder(orderFormDto);
            return OrderControllerHelper.redirectMyOrders();
        } catch (Exception e) {
            OrderControllerHelper.addErrorMessage(attributes, e.getMessage());
            return OrderControllerHelper.redirectCheckout();
        }
    }

    @GetMapping("/solicitar-troca/{orderId}")
    public ModelAndView showExchangeRequestPage(@PathVariable Long orderId, RedirectAttributes attributes) {
        try {
            Order order = orderService.findById(orderId);
            return OrderControllerHelper.prepareExchangeRequestPage(order);
        } catch (Exception e) {
            OrderControllerHelper.addErrorMessage(attributes, e.getMessage());
            return OrderControllerHelper.redirectOrdersUser();
        }
    }
}
