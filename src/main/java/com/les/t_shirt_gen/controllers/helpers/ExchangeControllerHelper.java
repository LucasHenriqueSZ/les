package com.les.t_shirt_gen.controllers.helpers;

import org.springframework.web.servlet.ModelAndView;

public class ExchangeControllerHelper extends CommonControllerHelper {

    public static ModelAndView redirectOrders() {
        return new ModelAndView("redirect:/pedidos");
    }
}
