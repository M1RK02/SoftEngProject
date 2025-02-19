package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

/**
 * Action to flip a card
 */
public class FlipCardAction extends RoomAction {
    /**
     * Id of the card to flip
     */
    private final int cardId;

    /**
     * Create a new FlipCardAction
     *
     * @param playerName of the player who is creating the action
     * @param room       where the action will be executed
     * @param cardId     of card to flip
     */
    public FlipCardAction(String playerName, RoomController room, int cardId) {
        super(playerName, room);
        this.cardId = cardId;
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.flipCard(getPlayerName(), cardId);
    }

}