package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

/**
 * Action to choose the secret objective
 */
public class ChooseSecretObjectiveAction extends RoomAction {
    /**
     * Id of the secret objective card chosen by the player
     */
    private final int cardId;

    /**
     * Create a new ChooseSecretObjectiveAction
     *
     * @param playerName of the player who is creating the action
     * @param room       where the action will be executed
     * @param cardId     chosen by the player
     */
    public ChooseSecretObjectiveAction(String playerName, RoomController room, int cardId) {
        super(playerName, room);
        this.cardId = cardId;
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.chooseSecretObjective(getPlayerName(), cardId);
    }
}