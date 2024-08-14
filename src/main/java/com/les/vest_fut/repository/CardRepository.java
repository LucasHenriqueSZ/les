package com.les.vest_fut.repository;

import com.les.vest_fut.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
