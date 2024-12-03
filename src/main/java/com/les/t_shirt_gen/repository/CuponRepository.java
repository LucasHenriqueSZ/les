package com.les.t_shirt_gen.repository;


import com.les.t_shirt_gen.model.payment.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Optional<Cupon> findByCode(String code);

    @Query("SELECT c FROM Cupon c WHERE c.user.id = :userId")
    List<Cupon> findAllByUserId(@Param("userId") Long userId);
}
