package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.player.Position;

/**
 * Action to play a card
 */
public class PlayCardAction extends RoomAction {
    /**
     * Id of the card to play
     */
    private final int cardId;
    /**
     * Position where to play the card
     */
    private final Position position;

    /**
     * Create a new PlayCardAction
     * @param playerName of the player who is creating the action
     * @param room where the action will be executed
     * @param cardId of the card to play
     * @param position where to play the card
     */
    public PlayCardAction(String playerName, RoomController room, int cardId, Position position) {
        super(playerName, room);
        this.cardId = cardId;
        this.position = position;
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.playCard(getPlayerName(), cardId, position);
    }
}