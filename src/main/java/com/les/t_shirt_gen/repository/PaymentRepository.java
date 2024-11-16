package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
