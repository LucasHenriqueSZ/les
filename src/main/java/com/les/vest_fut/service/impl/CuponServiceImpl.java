package com.les.vest_fut.service.impl;

import com.les.vest_fut.model.payment.Cupon;
import com.les.vest_fut.repository.CuponRepository;
import com.les.vest_fut.service.CuponService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CuponServiceImpl implements CuponService {

    private final CuponRepository cuponRepository;

    @Override
    public Optional<Cupon> validate(String code) {
        Optional<Cupon> optionalCupon = cuponRepository.findByCode(code);

        if (optionalCupon.isPresent()) {
            Cupon cupon = optionalCupon.get();
            if (!cupon.isUsed()) {
                return Optional.of(cupon);
            }
        }
        return Optional.empty();
    }
}
