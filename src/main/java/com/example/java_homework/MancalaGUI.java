package com.example.java_homework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The MancalaGUI class is the main entry point for the Mancala game application.
 * It extends the JavaFX Application class to manage the application lifecycle.
 */
public class MancalaGUI extends Application {

    /**
     * Initializes and starts the JavaFX application.
     * It loads the FXML file representing the UI and sets up the primary stage.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an exception occurs during the initialization or loading of the UI.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/java_homework/hello-view.fxml"));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Mancala Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main entry point for the Mancala game application.
     * It launches the JavaFX application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
