package it.polimi.ingsw.gc01.view.gui;

public enum SceneEnum {
    INTRO("/FXML/Intro.fxml"),
    SETUP("/FXML/SetUp.fxml"),
    MENU("/FXML/Menu.fxml"),
    JOIN_BY_ID("/FXML/JoinById.fxml"),
    WAITING_ROOM("/FXML/WaitingRoom.fxml"),
    CHOOSE_STARTER("/FXML/ChooseStarter.fxml"),
    CHOOSE_COLOR("/FXML/ChooseColor.fxml"),
    CHOOSE_OBJECTIVE("/FXML/ChooseObjective.fxml"),
    WAITING_OTHERS("/FXML/WaitingOthers.fxml"),
    PLAY("/FXML/Play.fxml"),
    DRAW_CARD("/FXML/DrawCard.fxml"),
    CURRENT_FIELD("/FXML/CurrentField.fxml"),
    CHOOSE_OTHER_FIELDS("/FXML/ChooseOtherFields.fxml"),
    TABLE_POINT("/FXML/TablePoint.fxml"),
    OBJECTIVES("/FXML/ShowObjectives.fxml"),
    WIN("/FXML/Win.fxml");

    private final String path;

    SceneEnum(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
