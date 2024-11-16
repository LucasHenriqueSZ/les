package com.les.t_shirt_gen.service;

import com.les.t_shirt_gen.model.users.Address;
import com.les.t_shirt_gen.model.users.Card;
import com.les.t_shirt_gen.model.users.UserEntity;

import java.util.List;

public interface ClientService {
    void saveClient(UserEntity client);

    void editBasicInfoClient(UserEntity client, Long id);

    void editPasswordClient(UserEntity client, String currentPassword, Long id);

    void removeCard(Long cardId, Long id);

    void saveCard(Card card, Long id);

    void saveAddress(Address address, Long id);

    void removeAddress(Long addressId, Long id);

    List<UserEntity> getAllClients();

    void alterStatus(Long id);

    void editPasswordAdmin(UserEntity client, Long clientId);
}
