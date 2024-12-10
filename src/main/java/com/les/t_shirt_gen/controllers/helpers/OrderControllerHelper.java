package com.les.t_shirt_gen.controllers.helpers;


import com.les.t_shirt_gen.model.order.Order;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class OrderControllerHelper extends CommonControllerHelper {

    public static ModelAndView redirectMyOrders() {
        return new ModelAndView("redirect:/pedidos");
    }

    public static ModelAndView redirectCheckout() {
        return new ModelAndView("redirect:/checkout");
    }

    public static ModelAndView redirectOrdersUser(){return new ModelAndView("redirect:/pedidos");}

    public static ModelAndView prepareOrdersUser(List<Order> orders) {
        ModelAndView mv = new ModelAndView("public/pages/user/orders");
        mv.addObject("orders", orders);
        return mv;
    }

    public static ModelAndView prepareExchangeRequestPage(Order order) {
        ModelAndView mv = new ModelAndView("public/pages/user/exchange-request-page");
        mv.addObject("order", order);
        return mv;
    }
}