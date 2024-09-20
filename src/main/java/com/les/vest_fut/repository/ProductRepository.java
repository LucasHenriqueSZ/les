package com.les.vest_fut.repository;

import com.les.vest_fut.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}