package com.les.vest_fut.repository;

import com.les.vest_fut.model.users.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
