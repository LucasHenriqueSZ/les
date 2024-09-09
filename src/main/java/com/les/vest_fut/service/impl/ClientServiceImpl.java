package com.les.vest_fut.service.impl;

import com.les.vest_fut.Enums.MessagesExceptions;
import com.les.vest_fut.exceptions.UniqueFieldException;
import com.les.vest_fut.model.users.Address;
import com.les.vest_fut.model.users.Card;
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
        UserEntity clientUpdate = this.getClientById(id);
        clientUpdate.setName(client.getName());
        clientUpdate.setCpf(client.getCpf());
        clientUpdate.setGender(client.getGender());
        clientUpdate.setEmail(client.getEmail());
        clientUpdate.setPhone(client.getPhone());
        clientRepository.save(clientUpdate);
    }

    @Override
    public void editPasswordClient(UserEntity client, String currentPassword, Long id) {
        UserEntity currentClient = this.getClientById(id);
        if (!passwordEncoder.matches(currentPassword, currentClient.getPassword())) {
            throw new IllegalArgumentException(MessagesExceptions.CURRENT_PASSWORD_INVALID.getMessage());
        }
        currentClient.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(currentClient);
    }

    @Override
    public void removeCard(Long cardId, Long id) {
        UserEntity client = this.getClientById(id);
        if (client.getCards().stream().noneMatch(card -> card.getId().equals(cardId))) {
            throw new RuntimeException(MessagesExceptions.CARD_NOT_FOUND.getMessage());
        }
        if (client.getCards().size() == 1) {
            throw new RuntimeException(MessagesExceptions.CARD_REQUIRED.getMessage());
        }
        client.getCards().removeIf(card -> card.getId().equals(cardId));
        clientRepository.save(client);
    }

    @Override
    public void saveCard(Card card, Long id) {
        UserEntity client = this.getClientById(id);
        if (card.getId() != null) {
            this.updateCard(card, client);
            return;
        }
        this.addNewCard(card, client);

    }

    @Override
    public void saveAddress(Address address, Long id) {
        UserEntity client = this.getClientById(id);
        if (address.getId() != null) {
            this.updateAddress(address, client);
            return;
        }
        this.addNewAddress(address, client);
    }

    @Override
    public void removeAddress(Long addressId, Long id) {
        UserEntity client = this.getClientById(id);
        if (client.getAddresses().stream().noneMatch(address -> address.getId().equals(addressId))) {
            throw new RuntimeException(MessagesExceptions.ADDRESS_NOT_FOUND.getMessage());
        }
        if (client.getAddresses().size() == 1) {
            throw new RuntimeException(MessagesExceptions.ADDRESS_REQUIRED.getMessage());
        }
        client.getAddresses().removeIf(address -> address.getId().equals(addressId));
        clientRepository.save(client);
    }

    @Override
    public List<UserEntity> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public void alterStatus(Long id) {
        UserEntity client = this.getClientById(id);
        client.setActive(!client.isActive());
        clientRepository.save(client);
    }

    @Override
    public void editPasswordAdmin(UserEntity client, Long clientId) {
        UserEntity currentClient = this.getClientById(clientId);
        currentClient.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.save(currentClient);
    }

    private void addNewAddress(Address address, UserEntity client) {
        this.validateUniqueAddress(address, client);
        client.getAddresses().add(address);
        clientRepository.save(client);
    }


    private void updateAddress(Address address, UserEntity client) {
        this.validateUniqueAddress(address, client);
        client.getAddresses().stream()
                .filter(a -> a.getId().equals(address.getId()))
                .findFirst()
                .ifPresent(a -> {
                    a.setZipCode(address.getZipCode());
                    a.setStreet(address.getStreet());
                    a.setNumber(address.getNumber());
                    a.setComplement(address.getComplement());
                    a.setNeighborhood(address.getNeighborhood());
                    a.setCity(address.getCity());
                    a.setState(address.getState());
                });
        clientRepository.save(client);
    }

    private void updateCard(Card card, UserEntity client) {
        this.validateUniqueCard(card, client);
        client.getCards().stream()
                .filter(c -> c.getId().equals(card.getId()))
                .findFirst()
                .ifPresent(c -> {
                    c.setFlag(card.getFlag());
                    c.setCardNumber(card.getCardNumber());
                    c.setCvv(card.getCvv());
                    c.setExpiryDate(card.getExpiryDate());
                    c.setHolderName(card.getHolderName());
                });
        clientRepository.save(client);
    }

    private void addNewCard(Card card, UserEntity client) {
        this.validateUniqueCard(card, client);
        client.getCards().add(card);
        clientRepository.save(client);
    }

    private void validateUniqueAddress(Address address, UserEntity client) {
        client.getAddresses().stream()
                .filter(a -> a.getZipCode().equals(address.getZipCode())
                        && a.getStreet().equals(address.getStreet())
                        && a.getNumber().equals(address.getNumber())
                        && (address.getId() == null || !a.getId().equals(address.getId()))
                )
                .findFirst()
                .ifPresent(a -> {
                    throw new UniqueFieldException(MessagesExceptions.ADDRESS_ALREADY_EXISTS);
                });
    }

    private void validateUniqueCard(Card card, UserEntity client) {
        client.getCards().stream()
                .filter(c -> c.getCardNumber().equals(card.getCardNumber())
                        && (card.getId() == null || !c.getId().equals(card.getId()))
                )
                .findFirst()
                .ifPresent(c -> {
                    throw new UniqueFieldException(MessagesExceptions.CARD_ALREADY_EXISTS);
                });
    }

    private UserEntity getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new RuntimeException(MessagesExceptions.CLIENT_NOT_FOUND.getMessage()));
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
