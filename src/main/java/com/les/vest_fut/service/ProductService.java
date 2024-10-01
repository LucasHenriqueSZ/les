package com.les.vest_fut.service;

import com.les.vest_fut.model.product.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(Long productId);
}
