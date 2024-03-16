package com.example.perfectmazeclient.views;

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
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!LoginValidation.validateLogin(username, password)) return;

        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, event, getClass());
    }

    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.MAIN_MENU, event, getClass());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }
}
