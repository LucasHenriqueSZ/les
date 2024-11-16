package com.les.t_shirt_gen.repository;

import com.les.t_shirt_gen.model.users.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
