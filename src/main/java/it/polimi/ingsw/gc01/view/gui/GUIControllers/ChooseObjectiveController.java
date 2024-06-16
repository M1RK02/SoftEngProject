package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;

import java.util.List;

public class ChooseObjectiveController extends GenericController {
    int selection = -1;
    private List<Integer> possibleObjectiveIds;
    @FXML
    private ImageView objectiveLeft;
    @FXML
    private ImageView objectiveRight;
    private ColorAdjust colorAdjustRight;
    private ColorAdjust colorAdjustLeft;

    @FXML
    private void chooseLeft() {
        selection = 0;
        colorAdjustLeft.setBrightness(0.5);
        colorAdjustRight.setBrightness(0);
        objectiveLeft.setEffect(colorAdjustLeft);
        objectiveRight.setEffect(colorAdjustRight);
    }

    @FXML
    private void chooseRight() {
        selection = 1;
        colorAdjustLeft.setBrightness(0);
        colorAdjustRight.setBrightness(0.5);
        objectiveLeft.setEffect(colorAdjustLeft);
        objectiveRight.setEffect(colorAdjustRight);
    }

    @FXML
    private void confirm() {
        if (selection == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an objective card");
            alert.showAndWait();
            return;
        }
            gui.chooseSecretObjective(possibleObjectiveIds.get(selection));
    }

    @Override
    public void setAttributes(Object... o) {
        this.possibleObjectiveIds = (List<Integer>) o[0];
        objectiveLeft.setImage(new Image(getClass().getResourceAsStream("/images/cards/Front" + possibleObjectiveIds.get(0) + ".png")));
        objectiveRight.setImage(new Image(getClass().getResourceAsStream("/images/cards/Front" + possibleObjectiveIds.get(1) + ".png")));
        colorAdjustRight = new ColorAdjust();
        colorAdjustLeft = new ColorAdjust();
    }
}
