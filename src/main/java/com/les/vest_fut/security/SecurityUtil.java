package com.les.vest_fut.security;

import com.les.vest_fut.model.users.UserEntity;
import com.les.vest_fut.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SecurityUtil {

    private final UserRepository repository;

    public UserEntity getUserSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return repository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado, verifique seu login"));
        }
        return null;
    }
}