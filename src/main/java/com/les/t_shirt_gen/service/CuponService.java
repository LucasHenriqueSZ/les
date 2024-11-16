package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.model.payment.Cupon;

import java.util.Optional;

public interface CuponService {

    Optional<Cupon> validate(String code);
}
