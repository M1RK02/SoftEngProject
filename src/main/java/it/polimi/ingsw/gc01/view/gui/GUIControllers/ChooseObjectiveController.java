package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.image.*;

import java.util.List;

public class ChooseObjectiveController extends GenericController {
    int selection = -1;
    private List<Integer> possibleObjectiveIds;
    @FXML
    private ImageView objectiveLeft;
    @FXML
    private ImageView objectiveRight;

    @FXML
    private void chooseLeft() {
        selection = 0;
    }

    @FXML
    private void chooseRight() {
        selection = 1;
    }

    @FXML
    private void confirm() {
        do {
            if (selection == -1) {
                gui.showError("Please select an objective card");
            }
        } while (selection == -1);
        gui.chooseSecretObjective(possibleObjectiveIds.get(selection));
    }

    @Override
    public void setAttribute(Object o) {
        this.possibleObjectiveIds = (List<Integer>) o;
        objectiveLeft.setImage(new Image(getClass().getResourceAsStream("images/card/Front" + possibleObjectiveIds.get(0) + ".png")));
        objectiveRight.setImage(new Image(getClass().getResourceAsStream("images/card/Front" + possibleObjectiveIds.get(1) + ".png")));
    }
}
