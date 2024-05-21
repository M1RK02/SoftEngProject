package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

public class FlipCardAction extends RoomAction {
    private final int cardId;

    public FlipCardAction(String playerName, RoomController room, int cardId) {
        super(playerName, room);
        this.cardId = cardId;
    }

    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.flipCard(getPlayerName(), cardId);
    }

}