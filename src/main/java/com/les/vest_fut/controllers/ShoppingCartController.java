package com.les.vest_fut.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carrinho-compras")
public class ShoppingCartController {

    @RequestMapping()
    public ModelAndView shoppingCart() {
        return new ModelAndView("public/pages/shopping-cart");
    }
}
