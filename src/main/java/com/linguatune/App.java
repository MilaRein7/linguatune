package com.linguatune;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/linguatune/view/main-view.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getResource("/com/linguatune/view/style.css")
                ).toExternalForm()
        );

        primaryStage.setTitle("LinguaTune – учим английский по песням");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @SuppressWarnings("unused")
    static void main(String[] args) {
        launch(args);
    }
}