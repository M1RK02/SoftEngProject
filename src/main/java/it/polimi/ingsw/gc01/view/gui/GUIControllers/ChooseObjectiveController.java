package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.util.List;

public class ChooseObjectiveController extends GenericController {
    private List<Integer> possibleObjectiveIds;
    @FXML
    private ImageView objectiveLeft;
    @FXML
    private ImageView objectiveRight;

    @FXML
    private void setPossibleObjectiveIds(List<Integer> possibleObjectiveIds) {
        this.possibleObjectiveIds = possibleObjectiveIds;
        objectiveLeft.setImage(new Image(getClass().getResourceAsStream("/cardfront/"+possibleObjectiveIds.get(0)+".png")));
        objectiveRight.setImage(new Image(getClass().getResourceAsStream("/cardfront/"+possibleObjectiveIds.get(1)+".png")));
    }

    @FXML
    private void actionChooseObjective(int objectiveId) {

    }
}
