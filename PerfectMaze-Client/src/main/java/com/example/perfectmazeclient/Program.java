package com.example.perfectmazeclient;


import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Program extends Application {

    @Override
    public void start(Stage primaryStage)  {
        PageLoader.init(primaryStage, getClass());
        PageLoader.loadPage(FXMLPaths.MAIN_MENU);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
