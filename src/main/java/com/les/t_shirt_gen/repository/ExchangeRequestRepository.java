package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.exchange.ExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Long> {
}
