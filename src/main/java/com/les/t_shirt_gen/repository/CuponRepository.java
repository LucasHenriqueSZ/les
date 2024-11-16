package com.les.t_shirt_gen.repository;


import com.les.t_shirt_gen.model.payment.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Optional<Cupon> findByCode(String code);
}
