package com.les.vest_fut.service.impl;

import com.les.vest_fut.model.cart.Cart;
import com.les.vest_fut.model.cart.CartItem;
import com.les.vest_fut.model.product.Product;
import com.les.vest_fut.model.users.UserEntity;
import com.les.vest_fut.repository.CartItemRepository;
import com.les.vest_fut.repository.CartRepository;
import com.les.vest_fut.repository.ProductRepository;
import com.les.vest_fut.security.SecurityUtil;
import com.les.vest_fut.service.ProductService;
import com.les.vest_fut.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ProductService productService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final SecurityUtil securityUtil;

    @Transactional
    public void addItemToCart(Long productId, Integer quantity) {
        UserEntity user = securityUtil.getUserSession();

        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto");
        }

        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new IllegalStateException("Carrinho não encontrado para o usuário logado")
        );

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            updateItemQuantity(existingItem, quantity);
        } else {
            addNewItemToCart(cart, product, quantity);
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        cartRepository.save(cart);
    }

    @Override
    public Cart getShoppingCart() {
        UserEntity user = securityUtil.getUserSession();
        return cartRepository.findByUser(user).orElseThrow(
                () -> new IllegalStateException("Carrinho não encontrado para o usuário logado")
        );
    }

    @Transactional
    @Override
    public void removeItemCart(Long itemCartId) {
        UserEntity user = securityUtil.getUserSession();
        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new IllegalStateException("Carrinho não encontrado para o usuário logado")
        );

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemCartId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item do carrinho não encontrado"));

        Product product = cartItem.getProduct();
        product.setStock(product.getStock() + cartItem.getQuantity());
        productRepository.save(product);
        cart.getItems().remove(cartItem);
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void increaseQuantityItem(Long itemCartId) {
        CartItem cartItem = cartItemRepository.findById(itemCartId).orElseThrow(
                () -> new IllegalArgumentException("Item do carrinho não encontrado")
        );
        Product product = cartItem.getProduct();
        if (product.getStock() < 1) {
            throw new IllegalArgumentException("Estoque insuficiente para aumentar a quantidade do produto");
        }
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        product.setStock(product.getStock() - 1);
        productRepository.save(product);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    @Override
    public void decreaseQuantityItem(Long itemCartId) {
        CartItem cartItem = cartItemRepository.findById(itemCartId).orElseThrow(
                () -> new IllegalArgumentException("Item do carrinho não encontrado")
        );
        Product product = cartItem.getProduct();

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            product.setStock(product.getStock() + 1);
            cartItemRepository.save(cartItem);
            productRepository.save(product);
        } else {
            Cart cart = cartItem.getCart();
            cart.getItems().remove(cartItem);
            product.setStock(product.getStock() + 1);
            productRepository.save(product);
            cartItemRepository.delete(cartItem);
        }
    }

    private void updateItemQuantity(CartItem item, int quantity) {
        item.setQuantity(item.getQuantity() + quantity);
        item.setTotalPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
    }

    private void addNewItemToCart(Cart cart, Product product, int quantity) {
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setCart(cart);
        newItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cart.getItems().add(newItem);
    }

    public void clearCartWithoutRestocking() {
        UserEntity user = securityUtil.getUserSession();

        Cart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new IllegalStateException("Carrinho não encontrado para o usuário logado")
        );
        cart.getItems().clear();
        cartRepository.save(cart);
    }

//    TODO:Mover para a criação de usuario
//    private Cart createNewCart(UserEntity user) {
//        Cart cart = new Cart();
//        cart.setUser(user);
//        return cart;
//    }
}