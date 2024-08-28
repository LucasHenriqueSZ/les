package com.les.vest_fut.service.impl;

import com.les.vest_fut.model.users.UserEntity;
import com.les.vest_fut.repository.UserRepository;
import com.les.vest_fut.service.UserService;
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
