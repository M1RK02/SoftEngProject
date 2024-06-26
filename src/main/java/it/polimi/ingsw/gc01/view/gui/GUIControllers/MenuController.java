package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;

/**
 * Controller of the Menu where you can choose if to create a newGame or joining an existing one
 */
public class MenuController extends GenericController {
    @FXML
    private void actionCreateNewGame() {
        gui.createGame();
    }

    @FXML
    private void actionJoinRandomGame() {
        gui.joinFirstGame();
    }

    @FXML
    private void actionJoinGame() {
        gui.askRoomId();
    }

    @FXML
    private void actionExit() {
        gui.switchToIntro();
    }
}
