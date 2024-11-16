package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.users.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
