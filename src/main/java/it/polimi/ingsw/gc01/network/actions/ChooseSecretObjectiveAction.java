package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.controller.exceptions.CardNotValidException;
import it.polimi.ingsw.gc01.controller.exceptions.NameNotValidException;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.player.Player;

public class ChooseSecretObjectiveAction extends RoomAction{
    private final int cardId;

    public ChooseSecretObjectiveAction(String playerName, RoomController room, int cardId){
        super(playerName, room);
        this.cardId = cardId;
    }

    @Override
    public void execute() {
        RoomController controller = getRoomController();
         controller.chooseSecretObjective(getPlayerName(), cardId);
        }
    }
}
