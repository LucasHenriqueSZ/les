package com.les.t_shirt_gen.controllers;

import com.les.t_shirt_gen.model.payment.Cupon;
import com.les.t_shirt_gen.service.CuponService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/api/cupons")
public class CuponController {

    private final CuponService cuponService;

    @GetMapping("/validate")
    public ResponseEntity<?> validateCupon(@RequestParam String code) {
        Optional<Cupon> optionalCupon = cuponService.validate(code);

        if (optionalCupon.isPresent()) {
            Cupon cupon = optionalCupon.get();
            return ResponseEntity.ok(cupon);
        } else {
            return ResponseEntity.status(400).body("Cupom inválido ou já utilizado.");
        }
    }

}
