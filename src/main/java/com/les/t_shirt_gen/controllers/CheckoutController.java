package com.les.t_shirt_gen.controllers;

import com.les.t_shirt_gen.Enums.CardFlag;
import com.les.t_shirt_gen.dto.OrderFormDto;
import com.les.t_shirt_gen.model.cart.Cart;
import com.les.t_shirt_gen.security.SecurityUtil;
import com.les.t_shirt_gen.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final ShoppingCartService cartService;
    private final SecurityUtil securityUtil;

    @RequestMapping()
    public ModelAndView shoppingCart(OrderFormDto orderFormDto) {
        Cart cart = cartService.getShoppingCart();
        ModelAndView mv = new ModelAndView("public/pages/checkout");
        mv.addObject("orderFormDto", orderFormDto);
        mv.addObject("cart", cart);
        mv.addObject("user", securityUtil.getUserSession());
        mv.addObject("cardFlags", CardFlag.getAll());
        return mv;
    }
}
