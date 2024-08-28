package com.les.vest_fut.utils;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHARACTER = Pattern.compile("[^a-zA-Z0-9]");

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < MIN_LENGTH) {
            return false;
        }
        if (!UPPERCASE.matcher(password).find()) {
            return false;
        }
        if (!LOWERCASE.matcher(password).find()) {
            return false;
        }
        if (!DIGIT.matcher(password).find()) {
            return false;
        }
        return SPECIAL_CHARACTER.matcher(password).find();
    }
}
