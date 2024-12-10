package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.model.payment.Cupon;
import com.les.t_shirt_gen.model.users.UserEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CuponService {

    Optional<Cupon> validate(String code);

    void generateNewCuponToUser(UserEntity user, BigDecimal amount);

    public List<Cupon> getCuponsByUser(Long userId);
}
