package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.model.users.UserEntity;

public interface UserService {
    UserEntity getUserById(Long id);
}
