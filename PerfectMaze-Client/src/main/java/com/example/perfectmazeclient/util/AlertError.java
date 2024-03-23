package com.example.perfectmazeclient.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertError {
    public static void showAlertError(String alertTitle, String alertHeader, String alertContent)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }
    public static void showAlertError(String alertTitle, String alertHeader, String alertContent, String fxmlPath)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);

        alert.setOnCloseRequest(event -> PageLoader.loadPage(fxmlPath, event, AlertError.class));

        alert.showAndWait();
    }
}
