package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.model.payment.Cupon;
import com.les.t_shirt_gen.repository.CuponRepository;
import com.les.t_shirt_gen.service.CuponService;
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
