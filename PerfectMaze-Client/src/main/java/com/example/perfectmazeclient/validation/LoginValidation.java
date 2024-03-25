package com.example.perfectmazeclient.validation;

import com.example.perfectmazeclient.validation.util.Validator;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginValidation {

    private static final String FIELD_ERROR_CLASS = "field-error";
    private Label emailFieldError;
    private Label passwordFieldError;
    private Label usernameFieldError;
    private TextField emailField;
    private TextField passwordField;
    private TextField usernameField;
    public LoginValidation(Label emailEmptyFieldError, Label passwordEmptyFieldError, TextField emailField, PasswordField passwordField)
    {
        this.emailFieldError = emailEmptyFieldError;
        this.passwordFieldError = passwordEmptyFieldError;
        this.emailField = emailField;
        this.passwordField = passwordField;
    }

    public LoginValidation(Label emailEmptyFieldError, Label passwordEmptyFieldError, Label usernameEmptyFieldError, TextField emailField, PasswordField passwordField, TextField usernameField)
    {
        this.emailFieldError = emailEmptyFieldError;
        this.passwordFieldError = passwordEmptyFieldError;
        this.usernameFieldError = usernameEmptyFieldError;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.usernameField = usernameField;
    }
    public boolean validateLogin() {

        boolean isValid = true;

        String email = emailField.getText();
        String password = passwordField.getText();

        resetErrorFields();

        if (Validator.isNullOrEmpty(email))
        {
            showErrorField(emailField, emailFieldError, "Please enter an email.");
            isValid = false;
        }
        if (Validator.isNullOrEmpty(password)) {
            showErrorField(passwordField, passwordFieldError, "Please enter a password.");
            isValid = false;
        }
        return isValid;
    }

    public boolean validateRegister() {

        boolean isValid = true;

        String email = emailField.getText();
        String password = passwordField.getText();
        String username = usernameField.getText();

        resetErrorFields();

        if (Validator.isNullOrEmpty(email)) {
            showErrorField(emailField, emailFieldError, "Please enter an email.");
            isValid = false;
        }
        if (Validator.isNullOrEmpty(username)) {
            showErrorField(usernameField, usernameFieldError, "Please enter a username.");
            isValid = false;
        }
        if (Validator.isNullOrEmpty(password)) {
            showErrorField(passwordField, passwordFieldError, "Please enter a password.");
            isValid = false;
        }
        if (!Validator.isNullOrEmpty(email) && !Validator.isValidEmail(email)) {
            showErrorField(emailField, emailFieldError, "Invalid email format.");
            isValid = false;
        }
        if (!Validator.isNullOrEmpty(password) && !Validator.isValidPassword(password)) {
            showErrorField(passwordField, passwordFieldError, "Password must contain at least: \n - 6 characters \n - 1 uppercase letter \n - 1 lowercase letter \n - 1 number \n - 1 special character.");
            isValid = false;
        }
        return isValid;
    }

    private void resetErrorFields()
    {
        emailFieldError.setVisible(false);
        emailFieldError.setManaged(false);

        passwordFieldError.setVisible(false);
        passwordFieldError.setManaged(false);

        if(usernameFieldError != null)
        {
            usernameFieldError.setVisible(false);
            usernameFieldError.setManaged(false);
        }

        emailField.getStyleClass().remove(FIELD_ERROR_CLASS);
        passwordField.getStyleClass().remove(FIELD_ERROR_CLASS);

        if(usernameField != null)
        {
            usernameField.getStyleClass().remove(FIELD_ERROR_CLASS);
        }
    }

    private void showErrorField(TextField field, Label errorField, String errorMessage)
    {
        field.getStyleClass().add(FIELD_ERROR_CLASS);
        errorField.setVisible(true);
        errorField.setManaged(true);
        errorField.setText(errorMessage);
    }
}
