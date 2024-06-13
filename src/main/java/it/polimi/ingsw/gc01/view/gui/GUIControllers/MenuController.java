package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class MenuController {
    private GUI gui;
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
    public void actionCreateNewGame(ActionEvent e) throws IOException {
        gui.createGame();
    }

    public void actionJoinRandomGame(ActionEvent e) throws IOException {
        gui.joinFirstGame();
    }

    public void actionJoinGame(ActionEvent e) throws IOException {
        gui.joinGame();
    }

    public void actionExit(ActionEvent e) {
        gui.goToFirstScene();
    }
}
