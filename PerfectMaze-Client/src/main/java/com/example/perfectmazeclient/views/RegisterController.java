package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.handlers.UserRequests;
import com.example.perfectmazeclient.validation.LoginValidation;
import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable  {

    private LoginValidation LoginValidation;
    public TextField emailField;
    public TextField usernameField;
    public PasswordField passwordField;

    @FXML
    private Label emailFieldError;
    @FXML
    private Label usernameFieldError;
    @FXML
    private Label passwordFieldError;
    @FXML
    private Label serverRequestFieldError;

    public void onRegisterButtonClick(ActionEvent actionEvent) {

        clearServerRequestError();

        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!LoginValidation.validateRegister()) return;

        try
        {
            UserRequests.handleRegisterRequest(email, password, username);
            PageLoader.loadPage(FXMLPaths.GAME_OPTIONS);
        }
        catch (RequestFailed e)
        {
            showServerRequestError(e.getMessage());
        }
    }


    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.MAIN_MENU);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailField.setFocusTraversable(false);
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);

        LoginValidation = new LoginValidation(emailFieldError, passwordFieldError, usernameFieldError, emailField, passwordField, usernameField);

        clearAllFields();
    }

    private void showServerRequestError(String message)
    {
        serverRequestFieldError.setText(message);
        serverRequestFieldError.setVisible(true);
        serverRequestFieldError.setManaged(true);
    }

    private void clearAllFields()
    {
        emailFieldError.setVisible(false);
        emailFieldError.setManaged(false);

        usernameFieldError.setVisible(false);
        usernameFieldError.setManaged(false);

        passwordFieldError.setVisible(false);
        passwordFieldError.setManaged(false);

        serverRequestFieldError.setVisible(false);
        serverRequestFieldError.setManaged(false);
    }

    private void clearServerRequestError()
    {
        serverRequestFieldError.setVisible(false);
        serverRequestFieldError.setManaged(false);
    }
}
