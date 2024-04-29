package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayerInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.*;

import java.util.List;

public class Controller {
    private Room model;
    private WaitingRoom waitingRoom;

    public Controller(Room model){
        this.model = model;
    }

    public Room getModel() {
        return model;
    }

    public void addPlayer(Player p) throws MaxPlayerInException, PlayerAlreadyInException {
        List<Player> players = waitingRoom.getPlayers();
        //First I check that the player is not already in the game
        // then I check if the game is already full
        if (players.stream()
                .noneMatch(x -> x.equals(p))) {
            if (players.size() + 1 <= DefaultValue.MaxNumOfPlayer) {
                waitingRoom.addPlayer(p.getName(), p.getColor());
                //listenersHandler.notify_playerJoined(this);
            } else {
                //listenersHandler.notify_JoinUnableGameFull(p, this);
                throw new MaxPlayerInException();
            }
        } else {
            //listenersHandler.notify_JoinUnableNicknameAlreadyIn(p);
            throw new PlayerAlreadyInException();
        }
    }
}
