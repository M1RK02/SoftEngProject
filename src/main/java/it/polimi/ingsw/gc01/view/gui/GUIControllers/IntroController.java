package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;

/**
 * Controller of the first scene to view when starting the application
 */
public class IntroController extends GenericController {
    @FXML
    private void handlePlayButton() {
        gui.play();
    }
}