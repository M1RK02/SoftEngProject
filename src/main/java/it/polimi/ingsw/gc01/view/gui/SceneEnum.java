package it.polimi.ingsw.gc01.view.gui;

public enum SceneEnum {
    INTRO("/FXML/Intro.fxml"),
    SETUP("/FXML/Setup.fxml"),
    MENU("/FXML/Menu.fxml"),
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
