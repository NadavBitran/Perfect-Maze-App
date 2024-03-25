package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.handlers.UserRequests;
import com.example.perfectmazeclient.validation.LoginValidation;
import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private LoginValidation LoginValidation;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Label serverRequestFieldError;
    @FXML
    private Label emailFieldError;
    @FXML
    private Label passwordFieldError;


    @FXML
    protected void onLoginButtonClick(ActionEvent event) {

        clearServerRequestError();

        String email = emailField.getText();
        String password = passwordField.getText();

        if(!LoginValidation.validateLogin()) return;

        try
        {
            UserRequests.handleLoginRequest(email, password);
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
        initializeFields();
        LoginValidation = new LoginValidation(emailFieldError, passwordFieldError, emailField, passwordField);
    }

    private void initializeFields() {
        emailField.setText("");
        passwordField.setText("");
        emailField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);

        clearAllFields();
    }

    private void clearAllFields() {
        emailFieldError.setVisible(false);
        emailFieldError.setManaged(false);

        passwordFieldError.setVisible(false);
        passwordFieldError.setManaged(false);

        serverRequestFieldError.setVisible(false);
        serverRequestFieldError.setManaged(false);
    }

    private void clearServerRequestError() {
        serverRequestFieldError.setVisible(false);
        serverRequestFieldError.setManaged(false);
    }
    private void showServerRequestError(String message) {
        serverRequestFieldError.setText(message);
        serverRequestFieldError.setVisible(true);
        serverRequestFieldError.setManaged(true);
    }
}
