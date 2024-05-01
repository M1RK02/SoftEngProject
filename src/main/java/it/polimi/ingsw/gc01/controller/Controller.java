package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayerInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.*;

import java.util.List;

public class Controller {
    private Room room;
    private WaitingRoom waitingRoom;

    private GameState state;

    public Controller() {
        this.waitingRoom = new WaitingRoom();
        this.state = GameState.WAITING;
    }

    public Room getRoom() {
        return room;
    }
    public WaitingRoom getWaitingRoom(){
        return waitingRoom;
    }

    public void addPlayer(String nickname, PlayerColor color) throws MaxPlayerInException, PlayerAlreadyInException {
        List<Player> players = waitingRoom.getPlayers();
        //First I check that the player is not already in the game
        // then I check if the game is already full
        if (players.stream()
                .map(Player::getName).noneMatch(x -> x.equals(nickname))) {
            if (players.size() + 1 <= DefaultValue.MaxNumOfPlayer) {
                waitingRoom.addPlayer(nickname, color);
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

    public void distributeCards(){
        for (Player p : room.getPlayers()){
            p.addCard(room.getResourceDeck().pick());
            p.addCard(room.getResourceDeck().pick());
            p.addCard(room.getGoldenDeck().pick());
            p.setPossibleObjective(room.getObjectiveDeck().pick());
            p.setPossibleObjective(room.getObjectiveDeck().pick());
        }
    }

    public void prepareGame() {
        room = new Room(waitingRoom.getPlayers());

        for (Player player : room.getPlayers()) {
            player.addCard(room.getStarterDeck().pick());
        }

    }

    public void startGame() {
        this.state = GameState.RUNNING;
    }

    public void nextPlayer() {
        room.setCurrentPlayer(room.getNextPlayer());
    }

    public void calculateStrategy() {
        List<Player> players = room.getPlayers();
        List<ObjectiveCard> commonObjectives = room.getCommonObjectives();
        for (Player p : players) {
            p.addObjectivePoints(commonObjectives.get(0).calculatePoints(p));
            p.addObjectivePoints(commonObjectives.get(1).calculatePoints(p));
            p.addObjectivePoints(p.getSecretObjective().calculatePoints(p));
        }
    }

    public void flipCard (PlayableCard card) {
        card.setFront(!card.isFront());
    }

    public void playCard (PlayableCard card, Position position) {
        room.getCurrentPlayer().playCard(card, position);
    }

    public void drawCard (PlayableCard card) {
        room.getVisibleCards().remove(card);
        //Aggiorna le vidible card
        room.getCurrentPlayer().getHand().add(card);

    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void changeStateIfTwenty () {
        Player currentPlayer = room.getCurrentPlayer();
        if (currentPlayer.getPoints() >= 20) {
            state = GameState.LAST_CIRCLE;
        }
    }

    public void endGame () {
        calculateStrategy();
        List<Player> winners = room.getWinners();
        // broadcast the winners message to everyone
    }
}
