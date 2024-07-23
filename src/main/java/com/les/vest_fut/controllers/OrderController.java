package com.les.vest_fut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pedidos")
public class OrderController {

    @RequestMapping()
    public ModelAndView orders() {
        return new ModelAndView("public/pages/user/orders");
    }
}
