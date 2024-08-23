package com.les.vest_fut.annotations.constraints;

import com.les.vest_fut.annotations.ValidPassword;
import com.les.vest_fut.utils.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return PasswordValidator.isStrongPassword(password);
    }
}