package com.les.vest_fut.controllers.helpers;


import org.springframework.web.servlet.ModelAndView;

public class CheckoutControllerHelper extends CommonControllerHelper{

    public static ModelAndView redirectMyOrders() {
        return new ModelAndView("redirect:/pedidos");
    }

    public static ModelAndView redirectCheckout() {
        return new ModelAndView("redirect:/checkout");
    }
}