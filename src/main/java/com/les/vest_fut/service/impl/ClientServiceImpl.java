package com.les.vest_fut.service.impl;

import com.les.vest_fut.Enums.MessagesExceptions;
import com.les.vest_fut.exceptions.UniqueFieldException;
import com.les.vest_fut.model.users.Role;
import com.les.vest_fut.model.users.UserEntity;
import com.les.vest_fut.repository.RoleRepository;
import com.les.vest_fut.repository.UserRepository;
import com.les.vest_fut.service.ClientService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final UserRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(UserRepository clientRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveClient(UserEntity client) {
        this.validateUniqueFields(client, null);
        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        if (role.isEmpty()) {
            throw new RuntimeException();
        }
        client.setRoles(List.of(role.get()));
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setConfirmPassword(client.getPassword());
        clientRepository.save(client);
    }

    @Override
    public void editBasicInfoClient(UserEntity client, Long id) {
        this.validateUniqueFields(client, id);
        UserEntity clientUpdate = clientRepository.findById(id).orElseThrow(() -> new RuntimeException(MessagesExceptions.CLIENT_NOT_FOUND.getMessage()));
        clientUpdate.setName(client.getName());
        clientUpdate.setCpf(client.getCpf());
        clientUpdate.setGender(client.getGender());
        clientUpdate.setEmail(client.getEmail());
        clientUpdate.setPhone(client.getPhone());
        clientRepository.save(clientUpdate);
    }

    @Override
    public void editPasswordClient(UserEntity client, String currentPassword, Long id) {
        UserEntity currentClient = clientRepository.findById(id).orElseThrow(() -> new RuntimeException(MessagesExceptions.CLIENT_NOT_FOUND.getMessage()));
        if (!passwordEncoder.matches(currentPassword, currentClient.getPassword())) {
            throw new IllegalArgumentException(MessagesExceptions.CURRENT_PASSWORD_INVALID.getMessage());
        }
        currentClient.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(currentClient);
    }

    @Override
    public void removeCard(Long cardId, Long id) {
        UserEntity client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException(MessagesExceptions.CLIENT_NOT_FOUND.getMessage()));
        if (client.getCards().stream().noneMatch(card -> card.getId().equals(cardId))) {
            throw new RuntimeException(MessagesExceptions.CARD_NOT_FOUND.getMessage());
        }
        if (client.getCards().size() == 1) {
            throw new RuntimeException(MessagesExceptions.CARD_REQUIRED.getMessage());
        }
        client.getCards().removeIf(card -> card.getId().equals(cardId));
        clientRepository.save(client);
    }

    private void validateUniqueFields(UserEntity client, Long clientId) {

        clientRepository.findByCpf(client.getCpf()).ifPresent(existingClient -> {
            if (!existingClient.getId().equals(clientId)) {
                throw new UniqueFieldException(MessagesExceptions.CPF_ALREADY_EXISTS);
            }
        });

        clientRepository.findByEmail(client.getEmail()).ifPresent(existingClient -> {
            if (!existingClient.getId().equals(clientId)) {
                throw new UniqueFieldException(MessagesExceptions.EMAIL_ALREADY_EXISTS);
            }
        });
    }


}
