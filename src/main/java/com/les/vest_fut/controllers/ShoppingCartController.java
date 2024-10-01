package com.les.vest_fut.controllers;

import com.les.vest_fut.Enums.MessagesSuccess;
import com.les.vest_fut.controllers.helpers.ShoppingCartControllerHelper;
import com.les.vest_fut.model.cart.Cart;
import com.les.vest_fut.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/carrinho-compras")
public class ShoppingCartController {

    private final ShoppingCartService service;

    @RequestMapping()
    public ModelAndView shoppingCart() {
        Cart cart = service.getShoppingCart();
        return ShoppingCartControllerHelper.prepareShoppingCartView(cart);
    }

    @PostMapping("/add")
    public ModelAndView addToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            RedirectAttributes attributes
    ) {
        try {
            service.addItemToCart(productId, quantity);
            ShoppingCartControllerHelper.addSuccessMessage(attributes, MessagesSuccess.CART_ADD_PRODUCT);
        } catch (Exception e) {
            ShoppingCartControllerHelper.addErrorMessage(attributes, e.getMessage());
        }
        return ShoppingCartControllerHelper.redirectProductsView();
    }

    @PostMapping("/remover-item")
    public ModelAndView removeItemCart(@RequestParam Long itemCartId,
                                       RedirectAttributes attributes) {
        try {
            service.removeItemCart(itemCartId);
            ShoppingCartControllerHelper.addSuccessMessage(attributes, MessagesSuccess.CART_REMOVE_ITEM);
        } catch (Exception e) {
            ShoppingCartControllerHelper.addErrorMessage(attributes, e.getMessage());
        }
        return ShoppingCartControllerHelper.redirectShoppingCartView();
    }

    @PostMapping("/aumentar-quantidade-item")
    public ModelAndView increaseQuantityItem(@RequestParam Long itemCartId,
                                             RedirectAttributes attributes) {
        try {
            service.increaseQuantityItem(itemCartId);
        } catch (Exception e) {
            ShoppingCartControllerHelper.addErrorMessage(attributes, e.getMessage());
        }
        return ShoppingCartControllerHelper.redirectShoppingCartView();
    }

    @PostMapping("/diminuir-quantidade-item")
    public ModelAndView decreaseQuantityItem(@RequestParam Long itemCartId,
                                             RedirectAttributes attributes) {
        try {
            service.decreaseQuantityItem(itemCartId);
        } catch (Exception e) {
            ShoppingCartControllerHelper.addErrorMessage(attributes, e.getMessage());
        }
        return ShoppingCartControllerHelper.redirectShoppingCartView();
    }
}
