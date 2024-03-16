package com.example.perfectmazeclient.util;

import javafx.scene.control.Alert;

public class AlertError {
    public static void showAlertError(String alertTitle, String alertHeader, String alertContent)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }
}
