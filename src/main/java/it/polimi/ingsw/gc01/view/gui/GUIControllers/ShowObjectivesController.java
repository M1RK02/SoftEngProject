package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowObjectivesController extends GenericController{
    @FXML
    private ImageView commonObjective1;
    @FXML
    private ImageView commonObjective2;
    @FXML
    private ImageView secretObjective;

    @Override
    public void setAttributes(Object... o) {
        int obj1Id = (Integer) o[0];
        int obj2Id = (Integer) o[1];
        int secretObjId = (Integer) o[2];
        Image obj1 = new Image(getClass().getResourceAsStream("/images/cards/Front" + obj1Id + ".png"));
        Image obj2 = new Image(getClass().getResourceAsStream("/images/cards/Front" + obj2Id + ".png"));
        Image secretObj = new Image(getClass().getResourceAsStream("/images/cards/Front" + secretObjId + ".png"));

        commonObjective1.setImage(obj1);
        commonObjective2.setImage(obj2);
        secretObjective.setImage(secretObj);
    }

    @FXML
    private void goBack(){
        gui.backToPlay();
    }
}
