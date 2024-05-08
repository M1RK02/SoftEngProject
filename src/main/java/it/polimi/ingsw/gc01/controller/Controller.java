package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.GoldenRequirementsException;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayerInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
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
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().add(room.getGoldenDeck().pick());
            p.getPossibleObjective().add(room.getObjectiveDeck().pick());
            p.getPossibleObjective().add(room.getObjectiveDeck().pick());
        }
    }

    public void prepareGame() {
        room = new Room(waitingRoom.getPlayers());

        for (Player player : room.getPlayers()) {
            player.getHand().add(room.getStarterDeck().pick());
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

    public void playCard (PlayableCard card, Position position) throws GoldenRequirementsException{
        if (card instanceof GoldenCard){
            if (!((GoldenCard) card).checkRequirements(room.getCurrentPlayer())){
                throw new GoldenRequirementsException();
            }
        }
        room.getCurrentPlayer().playCard(card, position);
    }

    public void drawCard(TablePosition position){
        if (position.equals(TablePosition.RESOURCEDECK) || position.equals(TablePosition.RESOURCERIGHT) || position.equals(TablePosition.RESOURCELEFT)){
            room.getCurrentPlayer().getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().put(position, room.getResourceDeck().pick());
        }
        if (position.equals(TablePosition.GOLDENDECK) || position.equals(TablePosition.GOLDENRIGHT) || position.equals(TablePosition.GOLDENLEFT)){
            room.getCurrentPlayer().getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().put(position, room.getGoldenDeck().pick());
        }
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
        state = GameState.ENDED;
        // broadcast the winners message to everyone
    }
}
