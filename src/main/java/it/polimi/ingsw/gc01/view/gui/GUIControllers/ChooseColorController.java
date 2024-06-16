package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.ColorAdjust;
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
    private ColorAdjust colorAdjustBlue;
    private ColorAdjust colorAdjustGreen;
    private ColorAdjust colorAdjustRed;
    private ColorAdjust colorAdjustYellow;


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
            glowButton(0.5,0,0,0);
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
            glowButton(0,0.5,0,0);
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
            glowButton(0,0,0.5,0);
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
            glowButton(0,0,0,0.5);

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Yellow is no more available");
            alert.showAndWait();
        }
    }

    private void glowButton(double brightness1, double brightness2, double brightness3, double brightness4){
        colorAdjustBlue.setBrightness(brightness1);
        colorAdjustGreen.setBrightness(brightness2);
        colorAdjustRed.setBrightness(brightness3);
        colorAdjustYellow.setBrightness(brightness4);
        bluePawn.setEffect(colorAdjustBlue);
        greenPawn.setEffect(colorAdjustGreen);
        redPawn.setEffect(colorAdjustRed);
        yellowPawn.setEffect(colorAdjustYellow);
    }

    @Override
    public void setAttributes(Object... o) {
        availableColors = (List<PlayerColor>) o[0];
        colorAdjustBlue = new ColorAdjust();
        colorAdjustGreen = new ColorAdjust();
        colorAdjustRed = new ColorAdjust();
        colorAdjustYellow = new ColorAdjust();
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