package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findByCpf(String cpf);

    Optional<UserEntity> findFirstByName(String name);
}
