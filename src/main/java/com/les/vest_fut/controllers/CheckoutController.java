package com.les.vest_fut.controllers;

import com.les.vest_fut.model.cart.Cart;
import com.les.vest_fut.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final ShoppingCartService cartService;

    @RequestMapping()
    public ModelAndView shoppingCart() {
        Cart cart = cartService.getShoppingCart();
        ModelAndView mv = new ModelAndView("public/pages/checkout");
        mv.addObject("cart", cart);
        return mv;
    }
}
