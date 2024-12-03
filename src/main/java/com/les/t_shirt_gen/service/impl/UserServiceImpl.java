package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.model.payment.Cupon;
import com.les.t_shirt_gen.model.users.UserEntity;
import com.les.t_shirt_gen.repository.UserRepository;
import com.les.t_shirt_gen.service.CuponService;
import com.les.t_shirt_gen.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CuponService cuponService;


    @Override
    public UserEntity getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);

        if (user != null) {
            user.setCupons(cuponService.getCuponsByUser(user.getId()));
        }

        return user;
    }
}
