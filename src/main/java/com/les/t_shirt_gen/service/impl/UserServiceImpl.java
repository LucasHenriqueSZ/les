package com.les.t_shirt_gen.service.impl;

import com.les.t_shirt_gen.model.users.UserEntity;
import com.les.t_shirt_gen.repository.UserRepository;
import com.les.t_shirt_gen.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
