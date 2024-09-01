package com.les.vest_fut.service;

import com.les.vest_fut.model.users.Address;
import com.les.vest_fut.model.users.Card;
import com.les.vest_fut.model.users.UserEntity;

public interface ClientService {
    void saveClient(UserEntity client);

    void editBasicInfoClient(UserEntity client, Long id);

    void editPasswordClient(UserEntity client, String currentPassword, Long id);

    void removeCard(Long cardId, Long id);

    void saveCard(Card card, Long id);

    void saveAddress(Address address, Long id);

    void removeAddress(Long addressId, Long id);
}
