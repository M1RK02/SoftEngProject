package it.polimi.ingsw.gc01.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creiamo un layout di base
        BorderPane root = new BorderPane();

        // Creiamo una scena con il layout di base
        Scene scene = new Scene(root, 800, 600);

        // Configuriamo la finestra principale
        primaryStage.setTitle("Codex Naturalis");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}