package com.les.t_shirt_gen.annotations.constraints;

import com.les.t_shirt_gen.annotations.ValidPassword;
import com.les.t_shirt_gen.utils.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return PasswordValidator.isStrongPassword(password);
    }
}