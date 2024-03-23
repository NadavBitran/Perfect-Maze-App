package com.example.perfectmazeclient;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("views/main-menu.fxml"));
        Scene scene = new Scene(root);



        primaryStage.setResizable(false);
        primaryStage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Perfect Maze");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
