package com.les.vest_fut.service.impl;

import com.les.vest_fut.Enums.MessagesExceptions;
import com.les.vest_fut.exceptions.UniqueFieldException;
import com.les.vest_fut.model.users.Role;
import com.les.vest_fut.model.users.User;
import com.les.vest_fut.repository.RoleRepository;
import com.les.vest_fut.repository.UserRepository;
import com.les.vest_fut.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final UserRepository clientRepository;
    private final RoleRepository roleRepository;

    public ClientServiceImpl(UserRepository clientRepository, RoleRepository roleRepository) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveClient(User client) {
        this.validateUniqueFields(client);
        Optional<Role> role = roleRepository.findByName("USER");
        if (role.isEmpty()) {
            throw new RuntimeException();
        }
        client.setRoles(List.of(role.get()));
        clientRepository.save(client);
    }

    private void validateUniqueFields(User client) {
        if (clientRepository.findByCpf(client.getCpf()).isPresent()) {
            throw new UniqueFieldException(MessagesExceptions.CPF_ALREADY_EXISTS);
        }

        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new RuntimeException(MessagesExceptions.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }
}
