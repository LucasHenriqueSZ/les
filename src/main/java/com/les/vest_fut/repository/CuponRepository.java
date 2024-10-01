package com.les.vest_fut.repository;


import com.les.vest_fut.model.payment.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Optional<Cupon> findByCode(String code);
}
