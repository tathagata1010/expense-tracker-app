package org.expenseTracker.authService.utils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationUtils {

    // Regular expression pattern for email validation
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // Regular expression pattern for password strength
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    // Validate email format
    public static void validateEmail(String email) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    // Validate password strength
    public static void validatePassword(String password) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Weak password. It should contain at least 8 characters, one uppercase letter, " +
                    "one lowercase letter, one digit, and one special character.");
        }
    }

    // Validate user email and password
    public Boolean validateUser(String email, String password) {
        try {
            validateEmail(email);
            validatePassword(password);
            System.out.println("Email format and password strength are valid.");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}