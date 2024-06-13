package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;

public class MenuController extends GenericController {
    @FXML
    private void actionCreateNewGame(){
        gui.createGame();
    }

    @FXML
    private void actionJoinRandomGame(){
        gui.joinFirstGame();
    }

    @FXML
    private void actionJoinGame(){
        gui.askRoomId();
    }

    @FXML
    private void actionExit() {
        gui.leave();
    }
}
