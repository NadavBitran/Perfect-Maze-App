package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.handlers.UserRequests;
import com.example.perfectmazeclient.util.AlertError;
import com.example.perfectmazeclient.validation.LoginValidation;
import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if(!LoginValidation.validateLogin(email, password)) return;

        try
        {
            UserRequests.handleLoginRequest(email, password);
            PageLoader.loadPage(FXMLPaths.GAME_OPTIONS);
        }
        catch (RequestFailed e)
        {
            AlertError.showAlertError("Error", "Login", e.getMessage());
        }
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.MAIN_MENU);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearFields();
    }

    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        emailField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }
}
