package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.List;

public class ChooseColorController extends GenericController {
    @FXML
    private ChoiceBox<String> availableColors;

    public void setAvailableColors(List<PlayerColor> availableColors) {
        for (PlayerColor playerColor : availableColors) {
            this.availableColors.getItems().add(playerColor.toString());
        }
    }

    @FXML
    private void handleNextButton() {
        gui.chooseColor(availableColors.getValue());
    }

}