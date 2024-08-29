package com.les.vest_fut.service;

import com.les.vest_fut.model.users.UserEntity;

public interface ClientService {
    void saveClient(UserEntity client);

    void editBasicInfoClient(UserEntity client, Long id);

    void editPasswordClient(UserEntity client, String currentPassword, Long id);
}
