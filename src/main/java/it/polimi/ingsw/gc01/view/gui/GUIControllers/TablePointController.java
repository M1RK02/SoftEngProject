package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.view.gui.ClientModel;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class TablePointController extends GenericController{
    @FXML
    private ImageView bluePawn;
    @FXML
    private ImageView greenPawn;
    @FXML
    private ImageView redPawn;
    @FXML
    private ImageView yellowPawn;
    int bluePoints = -1;
    int greenPoints = -1;
    int redPoints = -1;
    int yellowPoints = -1;


    @FXML
    private void goBack(){
        ClientModel clientModel = gui.getClientModel();
        if (gui.getPlayerName().equals(clientModel.getCurrentPlayer())){
            gui.backToPlay();
        } else {
            gui.backToOtherFields();
        }
    }

    @Override
    public void setAttributes(Object... o) {
        Map<PlayerColor, Integer> pawnPoints = (Map<PlayerColor, Integer>) o[0];
        bluePawn.setImage(new Image(getClass().getResourceAsStream("/images/pawns/BluePawn.png")));
        greenPawn.setImage(new Image(getClass().getResourceAsStream("/images/pawns/GreenPawn.png")));
        redPawn.setImage(new Image(getClass().getResourceAsStream("/images/pawns/RedPawn.png")));
        yellowPawn.setImage(new Image(getClass().getResourceAsStream("/images/pawns/YellowPawn.png")));

        bluePawn.setOpacity(0);
        greenPawn.setOpacity(0);
        redPawn.setOpacity(0);
        yellowPawn.setOpacity(0);

        if (pawnPoints.get(PlayerColor.BLUE) != null){
            bluePoints = pawnPoints.get(PlayerColor.BLUE);
            bluePawn.setOpacity(1.0);
            translate(bluePawn, bluePoints);
        }

        if (pawnPoints.get(PlayerColor.GREEN) != null) {
            greenPoints = pawnPoints.get(PlayerColor.GREEN);
            greenPawn.setOpacity(1.0);
            translate(greenPawn, greenPoints);
        }

        if (pawnPoints.get(PlayerColor.RED) != null){
            redPoints = pawnPoints.get(PlayerColor.RED);
            redPawn.setOpacity(1.0);
            translate(redPawn, redPoints);
        }
        if (pawnPoints.get(PlayerColor.YELLOW) != null) {
            yellowPoints = pawnPoints.get(PlayerColor.YELLOW);
            yellowPawn.setOpacity(1.0);
            translate(yellowPawn, yellowPoints);
        }
    }


    @FXML
    private void addPoints(){
        bluePoints++;
        translate(bluePawn, bluePoints);
    }

    private void translate(ImageView img, int points){
        int constX = 0, constY = 0;
        if (img.equals(bluePawn)) {
            constX = -10;
            constY = -10;
        }
        if (img.equals(greenPawn)) {
            constX = 10;
            constY = -10;
        }
        if (img.equals(redPawn)) {
            constX = 10;
            constY = 10;
        }
        if (img.equals(yellowPawn)) {
            constX = -10;
            constY = 10;
        }
        switch (points){
            case -1:
                //img.setOpacity(0.0);
                break;
            case 0:
                img.setX(544 + constX);
                img.setY(593 + constY);
                break;
            case 1:
                img.setX(614 + constX);
                img.setY(593 + constY);
                break;
            case 2:
                img.setX(685 + constX);
                img.setY(593 + constY);
                break;
            case 3:
                img.setX(720 + constX);
                img.setY(527 + constY);
                break;
            case 4:
                img.setX(650 + constX);
                img.setY(527 + constY);
                break;
            case 5:
                img.setX(580 + constX);
                img.setY(527 + constY);
                break;
            case 6:
                img.setX(510 + constX);
                img.setY(527 + constY);
                break;
            case 7:
                img.setX(510 + constX);
                img.setY(463 + constY);
                break;
            case 8:
                img.setX(580 + constX);
                img.setY(463 + constY);
                break;
            case 9:
                img.setX(650 + constX);
                img.setY(463 + constY);
                break;
            case 10:
                img.setX(720 + constX);
                img.setY(463 + constY);
                break;
            case 11:
                img.setX(720 + constX);
                img.setY(399 + constY);
                break;
            case 12:
                img.setX(650 + constX);
                img.setY(399 + constY);
                break;
            case 13:
                img.setX(580 + constX);
                img.setY(399 + constY);
                break;
            case 14:
                img.setX(510 + constX);
                img.setY(399 + constY);
                break;
            case 15:
                img.setX(510 + constX);
                img.setY(335 + constY);
                break;
            case 16:
                img.setX(580 + constX);
                img.setY(335 + constY);
                break;
            case 17:
                img.setX(650 + constX);
                img.setY(335 + constY);
                break;
            case 18:
                img.setX(720 + constX);
                img.setY(335 + constY);
                break;
            case 19:
                img.setX(720 + constX);
                img.setY(271 + constY);
                break;
            case 20:
                img.setX(614 + constX);
                img.setY(238 + constY);
                break;
            case 21:
                img.setX(509 + constX);
                img.setY(269 + constY);
                break;
            case 22:
                img.setX(509 + constX);
                img.setY(204 + constY);
                break;
            case 23:
                img.setX(509 + constX);
                img.setY(139 + constY);
                break;
            case 24:
                img.setX(550 + constX);
                img.setY(86 + constY);
                break;
            case 25:
                img.setX(614 + constX);
                img.setY(75 + constY);
                break;
            case 26:
                img.setX(681 + constX);
                img.setY(86 + constY);
                break;
            case 27:
                img.setX(721 + constX);
                img.setY(139 + constY);
                break;
            case 28:
                img.setX(721 + constX);
                img.setY(203 + constY);
                break;
            case 29:
                img.setX(615 + constX);
                img.setY(153 + constY);
                break;
        }
    }


}
