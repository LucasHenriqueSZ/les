package com.les.vest_fut.controllers;

import com.les.vest_fut.controllers.helpers.CheckoutControllerHelper;
import com.les.vest_fut.dto.OrderFormDto;
import com.les.vest_fut.model.order.Order;
import com.les.vest_fut.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
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
        return CheckoutControllerHelper.prepareOrdersUser(orders);
    }

    @PostMapping()
    public ModelAndView newOrder(OrderFormDto orderFormDto, RedirectAttributes attributes) {
        try {
            orderService.createOrder(orderFormDto);
            return CheckoutControllerHelper.redirectMyOrders();
        } catch (Exception e) {
            CheckoutControllerHelper.addErrorMessage(attributes, e.getMessage());
            return CheckoutControllerHelper.redirectCheckout();
        }
    }
}
