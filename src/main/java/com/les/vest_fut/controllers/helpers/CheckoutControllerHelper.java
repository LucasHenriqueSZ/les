package com.les.vest_fut.controllers.helpers;


import com.les.vest_fut.model.order.Order;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class CheckoutControllerHelper extends CommonControllerHelper {

    public static ModelAndView redirectMyOrders() {
        return new ModelAndView("redirect:/pedidos");
    }

    public static ModelAndView redirectCheckout() {
        return new ModelAndView("redirect:/checkout");
    }

    public static ModelAndView prepareOrdersUser(List<Order> orders) {
        ModelAndView mv = new ModelAndView("public/pages/user/orders");
        mv.addObject("orders", orders);
        return mv;
    }
}