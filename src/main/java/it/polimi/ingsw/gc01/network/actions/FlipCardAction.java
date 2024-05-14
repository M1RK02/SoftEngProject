package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.controller.exceptions.CardNotValidException;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.player.Player;

public class FlipCardAction extends RoomAction{
    private int cardId;

    public FlipCardAction(String playerName, RoomController room, int cardId){
        super(playerName, room);
        this.cardId = cardId;
    }

    @Override
    public void execute(){
        RoomController controller = getRoomController();
        Player playerWhoIsFlipping = controller.getRoom().getPlayerByName(getPlayerName());
        PlayableCard cardToFlip = null;
        boolean found = false;

        for (PlayableCard card : playerWhoIsFlipping.getHand()){
            if (card.getId() == cardId){
                cardToFlip = card;
                found = true;
            }
        }

        if (!found){
            throw new CardNotValidException();
        }

        controller.flipCard(cardToFlip);
    }

}
