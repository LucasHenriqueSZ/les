package com.les.t_shirt_gen.controllers.helpers;

import com.les.t_shirt_gen.model.cart.Cart;
import org.springframework.web.servlet.ModelAndView;

public class ShoppingCartControllerHelper extends CommonControllerHelper {

    public static ModelAndView prepareShoppingCartView(Cart cart) {
        ModelAndView mv = new ModelAndView("public/pages/shopping-cart");
        mv.addObject("cart", cart);
        return mv;
    }

    public static ModelAndView redirectShoppingCartView() {
        return new ModelAndView("redirect:/carrinho-compras");
    }

    public static ModelAndView redirectProductsView() {
        return new ModelAndView("redirect:/produtos");
    }


}

