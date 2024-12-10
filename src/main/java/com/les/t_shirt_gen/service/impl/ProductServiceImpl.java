package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.model.product.Product;
import com.les.t_shirt_gen.repository.ProductRepository;
import com.les.t_shirt_gen.service.ProductService;
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
