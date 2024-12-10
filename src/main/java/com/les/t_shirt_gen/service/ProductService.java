package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(Long productId);
}
