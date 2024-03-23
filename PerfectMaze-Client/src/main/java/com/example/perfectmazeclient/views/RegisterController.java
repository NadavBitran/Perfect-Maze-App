package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.validation.LoginValidation;
import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable  {

    public TextField emailField;
    public TextField usernameField;
    public TextField passwordField;

    public void onRegisterButtonClick(ActionEvent actionEvent) {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!LoginValidation.validateRegister(email, username, password)) return;

        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }


    @FXML
    protected void onBackButtonClick(ActionEvent event) {
        PageLoader.loadPage(FXMLPaths.MAIN_MENU, event, getClass());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailField.setFocusTraversable(false);
        usernameField.setFocusTraversable(false);
        passwordField.setFocusTraversable(false);
    }
}
