package it.polimi.ingsw.gc01.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        //Creo una root, uno stage e una scena
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Intro.fxml"));
        primaryStage.setTitle("CodexNaturalis");


        //immagine d'icona
        Image icon = new Image(getClass().getResource("/images/CodexNaturalis.png").toExternalForm());
        primaryStage.getIcons().add(icon);

        //testo d'icona
        primaryStage.setTitle("CodexNaturalis");

        //passo allo stage la nuova scena che prende come radice dell'albero degli oggetti intr.fxml
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}