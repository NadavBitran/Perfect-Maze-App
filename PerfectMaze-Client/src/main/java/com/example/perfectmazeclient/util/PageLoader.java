package com.example.perfectmazeclient.util;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class PageLoader {
    private static Stage primaryStage;
    private static Class c;

    public static void init(Stage stage, Class nc) {
        primaryStage = stage;
        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.setTitle("Perfect Maze");
        c = nc;
    }
    public static void loadPage(String fxmlPath)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(c.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    public static void closePage(ActionEvent event)
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
