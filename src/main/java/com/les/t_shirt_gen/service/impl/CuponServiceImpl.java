package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.model.payment.Cupon;
import com.les.t_shirt_gen.model.payment.CuponType;
import com.les.t_shirt_gen.model.users.UserEntity;
import com.les.t_shirt_gen.repository.CuponRepository;
import com.les.t_shirt_gen.service.CuponService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CuponServiceImpl implements CuponService {

    private final CuponRepository cuponRepository;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

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


    @Override
    public void generateNewCuponToUser(UserEntity user, BigDecimal amount) {
        String code = generateCuponCode(user.getName());

        Cupon cupon = new Cupon();
        cupon.setCode(code);
        cupon.setAmount(amount);
        cupon.setUser(user);
        cupon.setType(CuponType.FIXED_AMOUNT);
        cupon.setUsed(false);

        cuponRepository.save(cupon);
    }

    private String generateCuponCode(String userName) {
        String prefix = "TRC" + userName.substring(0, 2).toUpperCase();

        String randomLetters = generateRandomString();

        String randomNumbers = generateRandomNumbers();

        return prefix + randomLetters + randomNumbers;
    }

    private String generateRandomString() {
        StringBuilder randomString = new StringBuilder(2);
        for (int i = 0; i < 2; i++) {
            randomString.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return randomString.toString();
    }

    private String generateRandomNumbers() {
        StringBuilder randomNumbers = new StringBuilder(2);
        for (int i = 0; i < 2; i++) {
            randomNumbers.append(RANDOM.nextInt(10));
        }
        return randomNumbers.toString();
    }

    @Override
    public List<Cupon> getCuponsByUser(Long userId) {
        return cuponRepository.findAllByUserId(userId);
    }
}
