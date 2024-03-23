package com.example.perfectmazeclient.validation;

import com.example.perfectmazeclient.util.AlertError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidation {
    public static boolean validateLogin(String email, String password) {
        if (email == null || email.isEmpty()) {
            AlertError.showAlertError("Error", "Login", "Please enter a email.");
            return false;
        }
        if (password == null || password.isEmpty()) {
            AlertError.showAlertError("Error", "Login", "Please enter a password.");
            return false;
        }
        return true;
    }

    public static boolean validateRegister(String email, String username, String password) {
        if (email == null || email.isEmpty()) {
            AlertError.showAlertError("Error", "Register", "Please enter an email.");
            return false;
        }
        if (username == null || username.isEmpty()) {
            AlertError.showAlertError("Error", "Register", "Please enter a username.");
            return false;
        }
        if (password == null || password.isEmpty()) {
            AlertError.showAlertError("Error", "Register", "Please enter a password.");
            return false;
        }
        if (!isValidEmail(email)) {
            AlertError.showAlertError("Error", "Register", "Invalid email format.");
            return false;
        }
        if (!isValidPassword(password)) {
            AlertError.showAlertError("Error", "Register", "Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one number, and one special character.");
            return false;
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean isValidPassword(String password) {
        // Regular expression for password validation
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
