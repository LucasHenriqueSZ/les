package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}