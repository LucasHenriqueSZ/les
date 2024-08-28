package com.les.vest_fut.service;

import com.les.vest_fut.Enums.MessagesExceptions;
import com.les.vest_fut.exceptions.UniqueFieldException;
import com.les.vest_fut.model.Client;
import com.les.vest_fut.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void saveClient(Client client) {
        this.validateUniqueFields(client);
        //TODO: criptografar senha
        clientRepository.save(client);
    }

    private void validateUniqueFields(Client client) {
        if (clientRepository.findByCpf(client.getCpf()).isPresent()) {
            throw new UniqueFieldException(MessagesExceptions.CPF_ALREADY_EXISTS);
        }
        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new RuntimeException(MessagesExceptions.EMAIL_ALREADY_EXISTS.getMessage());
        }
    }
}
