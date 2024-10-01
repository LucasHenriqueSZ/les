package com.les.vest_fut.service;

import com.les.vest_fut.model.payment.Cupon;

import java.util.Optional;

public interface CuponService {

    Optional<Cupon> validate(String code);
}
