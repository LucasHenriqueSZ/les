package com.les.t_shirt_gen.unit.utils;


import com.les.t_shirt_gen.utils.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordValidatorTest {


    @Test
    public void testValidPassword() {
        assertTrue(PasswordValidator.isStrongPassword("Example@123"), "Senha válida falhou");
    }

    @Test
    public void testPasswordTooShort() {
        assertFalse(PasswordValidator.isStrongPassword("Ex@1"), "Senha curta passou");
    }

    @Test
    public void testPasswordNoUpperCase() {
        assertFalse(PasswordValidator.isStrongPassword("example@123"), "Senha sem letra maiúscula passou");
    }

    @Test
    public void testPasswordNoLowerCase() {
        assertFalse(PasswordValidator.isStrongPassword("EXAMPLE@123"), "Senha sem letra minúscula passou");
    }

    @Test
    public void testPasswordNoDigit() {
        assertFalse(PasswordValidator.isStrongPassword("Example@Password"), "Senha sem número passou");
    }

    @Test
    public void testPasswordNoSpecialCharacter() {
        assertFalse(PasswordValidator.isStrongPassword("Example123"), "Senha sem caractere especial passou");
    }
}
