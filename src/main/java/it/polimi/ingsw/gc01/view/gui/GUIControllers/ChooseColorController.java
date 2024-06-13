package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.List;

public class ChooseColorController extends GenericController {
    @FXML
    private ChoiceBox<String> availableColors;

    @FXML
    private void handleNextButton() {
        gui.chooseColor(availableColors.getValue());
    }

    @Override
    public void setAttributes(Object... o) {
        List<PlayerColor> availableColors = (List<PlayerColor>) o[0];
        for (PlayerColor playerColor : availableColors) {
            this.availableColors.getItems().add(playerColor.toString());
        }
    }
}