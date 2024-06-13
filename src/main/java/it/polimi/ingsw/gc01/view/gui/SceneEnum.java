package it.polimi.ingsw.gc01.view.gui;

public enum SceneEnum {
    INTRO("/FXML/Intro.fxml"),
    SETUP("/FXML/SetUp.fxml"),
    MENU("/FXML/Menu.fxml"),
    JOIN_BY_ID("/FXML/JoinById.fxml"),
    WAITING_ROOM("/FXML/WaitingRoom.fxml"),
    CHOOSE_OBJECTIVE_CARD("/FXML/ChooseObjectiveCard.fxml");


    private final String value;


    SceneEnum(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
