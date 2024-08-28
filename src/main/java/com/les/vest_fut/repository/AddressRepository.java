package com.les.vest_fut.repository;

import com.les.vest_fut.model.users.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
