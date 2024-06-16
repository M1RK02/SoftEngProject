package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ChooseColorController extends GenericController {

    @FXML
    private ImageView bluePawn;
    @FXML
    private ImageView greenPawn;
    @FXML
    private ImageView redPawn;
    @FXML
    private ImageView yellowPawn;
    private List<PlayerColor> availableColors;
    private String choice = "";


    @FXML
    private void handleNextButton() {
        if (choice.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must choose a color");
            alert.showAndWait();
            return;
        }
        gui.chooseColor(choice);
    }

    @FXML
    private void setBluePawn(){
        if (availableColors.contains(PlayerColor.BLUE)) {
            choice = "BLUE";
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Blue is no more available");
            alert.showAndWait();
        }
    }

    @FXML
    private void setGreenPawn(){
        if (availableColors.contains(PlayerColor.GREEN)) {
            choice = "GREEN";
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Green is no more available");
            alert.showAndWait();
        }
    }

    @FXML
    private void setRedPawn(){
        if (availableColors.contains(PlayerColor.RED)) {
            choice = "RED";
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("RED is no more available");
            alert.showAndWait();
        }
    }

    @FXML
    private void setYellowPawn(){
        if (availableColors.contains(PlayerColor.YELLOW)) {
            choice = "YELLOW";
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Yellow is no more available");
            alert.showAndWait();
        }
    }

    @Override
    public void setAttributes(Object... o) {
        availableColors = (List<PlayerColor>) o[0];
        Image blue = new Image(getClass().getResourceAsStream("/images/pawns/BluePawn.png"));
        Image green = new Image(getClass().getResourceAsStream("/images/pawns/GreenPawn.png"));
        Image red = new Image(getClass().getResourceAsStream("/images/pawns/RedPawn.png"));
        Image yellow = new Image(getClass().getResourceAsStream("/images/pawns/YellowPawn.png"));
        bluePawn.setImage(blue);
        greenPawn.setImage(green);
        redPawn.setImage(red);
        yellowPawn.setImage(yellow);
        for (PlayerColor playerColor : availableColors) {
            if (playerColor.equals(PlayerColor.BLUE)) bluePawn.setOpacity(1.0);
            if (playerColor.equals(PlayerColor.GREEN)) greenPawn.setOpacity(1.0);
            if (playerColor.equals(PlayerColor.RED)) redPawn.setOpacity(1.0);
            if (playerColor.equals(PlayerColor.YELLOW)) yellowPawn.setOpacity(1.0);
        }

    }
}