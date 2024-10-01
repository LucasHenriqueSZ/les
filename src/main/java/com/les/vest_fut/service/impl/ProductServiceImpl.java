package com.les.vest_fut.service.impl;

import com.les.vest_fut.model.product.Product;
import com.les.vest_fut.repository.ProductRepository;
import com.les.vest_fut.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return repository.findById(productId);
    }
}
