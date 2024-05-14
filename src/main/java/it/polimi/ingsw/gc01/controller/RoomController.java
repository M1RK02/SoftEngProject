package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.*;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class RoomController {
    private MainController mainController;
    private Room room;
    private WaitingRoom waitingRoom;
    private GameState state;

    public RoomController() {
        this.waitingRoom = new WaitingRoom();
        this.state = GameState.WAITING;
    }

    /**
     *
     * @return the Main controller instance (Singleton)
     */
    public MainController getMainController() {
        return MainController.getInstance();
    }

    /**
     *
     * @return the Room of the started game
     */
    public Room getRoom() {
        return room;
    }

    /**
     *
     * @return the waitingRoom of the game waiting for starting
     */
    public WaitingRoom getWaitingRoom(){
        return waitingRoom;
    }

    /**
     *
     * @return the state of the game
     */
    public GameState getState() {
        return state;
    }

    /**
     *
     * @param state set the state of the game
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     *
     * @return the roomId associated to the controller
     */
    public String getRoomId(){
        return waitingRoom.getRoomId();
    }

    /**
     *
     * @return the number of players playing in the Room
     */
    public int getNumOfPlayers(){
        return room.getPlayers().size();
    }

    /**
     *
     * @return the number of players waiting in the WaitingRoom
     */
    public int getNumOfWaitingPlayers(){
        return waitingRoom.getNumOfPlayers();
    }

    /**
     *
     * @return the list of players currently playing
     */
    public List<Player> getPlayers(){
        return room.getPlayers();
    }

    /**
     *
     * @return the list of players currently waiting
     */
    public List<Player> getWaitingPlayers(){
        return waitingRoom.getPlayers();
    }


    /**
     *
     * @param playerName name of the players that needs to be added to the WaitingRoom
     * @throws MaxPlayersInException if there are already 4 players in the room
     * @throws PlayerAlreadyInException if there is already a player who is using this nickname
     */
    public void addPlayer(String playerName) throws PlayerAlreadyInException, MaxPlayersInException{
        List<Player> players = waitingRoom.getPlayers();
        //First I check that the game isn't full
        //then I check if the player is already in
        //then I check if the color is not already taken
        if (players.size() + 1 <= DefaultValue.MaxNumOfPlayer) {
            if (players.stream().map(Player::getName).noneMatch(x -> x.equals(playerName))) {
                waitingRoom.addPlayer(playerName);
            } else {
                //listenersHandler.notify_JoinUnableNicknameAlreadyIn(p);
                throw new PlayerAlreadyInException();
            }
        } else {
            throw new MaxPlayersInException();
        }
    }


    /**
     * Create the room and gives to all the players 1 Starter Card
     */
    public void prepareGame() {
        room = new Room(waitingRoom.getRoomId(), waitingRoom.getPlayers(), waitingRoom.getNotifier());

        for (Player player : room.getPlayers()) {
            player.getHand().add(room.getStarterDeck().pick());
        }

    }

    /**
     * for each player distribute 2 Resource Card, 1 Golden Card and 2 Objective Cards
     */
    public void distributeCards(){
        for (Player p : room.getPlayers()){
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().add(room.getGoldenDeck().pick());
            p.getPossibleObjectives().add(room.getObjectiveDeck().pick());
            p.getPossibleObjectives().add(room.getObjectiveDeck().pick());
        }
    }

    /**
     * Starts the game by setting the gamestate to RUNNING
     */
    public void startGame() {
        this.state = GameState.RUNNING;
    }


    /**
     * Change the currentPlayer to the next Player in the circle
     */
    public void nextPlayer() {
        room.setCurrentPlayer(room.getNextPlayer());
    }

    /**
     * Calculate all the points done by the strategies (both Common and the Secret one) for every player
     */
    public void calculateStrategy() {
        List<Player> players = room.getPlayers();
        List<ObjectiveCard> commonObjectives = room.getCommonObjectives();
        for (Player p : players) {
            p.addObjectivePoints(commonObjectives.get(0).calculatePoints(p));
            p.addObjectivePoints(commonObjectives.get(1).calculatePoints(p));
            p.addObjectivePoints(p.getSecretObjective().calculatePoints(p));
        }
    }

    /**
     * if the current players reach 20 or more points the game state is switched to the last circle (1 last turn for each player)
     */
    public void changeStateIfTwenty () {
        Player currentPlayer = room.getCurrentPlayer();
        if (currentPlayer.getPoints() >= 20) {
            state = GameState.LAST_CIRCLE;
        }
    }

    /**
     * Calculates all the points done by players, decrees a winner and set the game state to Ended
     */
    public void endGame () {
        calculateStrategy();
        List<Player> winners = room.getWinners();
        state = GameState.ENDED;
        // broadcast the winners message to everyone
    }


    /**
     *
     * @param player the player to set the color
     * @param color the color to set to the player who is choosing
     */
    public void chooseColor(Player player, PlayerColor color){
        player.setColor(color);
    }

    /**
     *
     * @param player the player to set ready or unready
     */
    public void switchReady(Player player){
        player.switchReady();
    }

    /**
     * Set the secret objective for a player
     *
     * @param player the player that is choosing the objective card
     * @param card the objective choosen by the player
     */
    public void chooseSecretObjective(Player player, ObjectiveCard card){
        player.setSecretObjective(card);
    }

    /**
     * Flip a card
     *
     * @param card the card that needs to be flipped
     */
    public void flipCard (PlayableCard card) {
        card.setFront(!card.isFront());
    }

    /**
     * Play a card
     *
     * @param card the card that needs to be played
     * @param position the position of the player field in which the card needs to be played
     * @throws GoldenRequirementsException the player doesn't has the necessary resource to play that card
     */
    public void playCard (Player player, PlayableCard card, Position position) throws GoldenRequirementsException, NotPlayerTurnException {
        if (!room.getCurrentPlayer().equals(player)){
            //TODO da cambiare
            throw new NotPlayerTurnException();
        }

        if (card.isFront()){
            if (card instanceof GoldenCard){
                if (!((GoldenCard) card).checkRequirements(room.getCurrentPlayer())){
                    throw new GoldenRequirementsException();
                }
            }
        }
        player.playCard(card, position);
    }

    /**
     * Draw a card from the drawable Cards in the table
     *
     * @param player the player who is trying to draw a card
     * @param position the position where the player wants to draw
     */
    public void drawCard(Player player, TablePosition position){
        if (!room.getCurrentPlayer().equals(player)){
            //TODO da cambiare
            throw new NotPlayerTurnException();
        }

        if (position.equals(TablePosition.RESOURCEDECK)){
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().put(position, room.getResourceDeck().pick());
        }
        if (position.equals(TablePosition.RESOURCERIGHT) || position.equals(TablePosition.RESOURCELEFT)){
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().put(position, room.getResourceDeck().pick());
            room.getDrawableCards().get(position).setFront(true);
        }
        if (position.equals(TablePosition.GOLDENDECK)){
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().put(position, room.getGoldenDeck().pick());
        }
        if (position.equals(TablePosition.GOLDENRIGHT) || position.equals(TablePosition.GOLDENLEFT)){
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().put(position, room.getGoldenDeck().pick());
            room.getDrawableCards().get(position).setFront(true);
        }

        room.setCurrentPlayer(room.getNextPlayer());
    }

    /**
     * Leave a player from a game
     *
     * @param player the player who wants to leave
     */
    public void leave(Player player){
        if (room == null) {
            waitingRoom.removePlayer(player);
            if (waitingRoom.getPlayers().isEmpty()) {
                try {
                    mainController.deleteRoom(waitingRoom.getRoomId());
                } catch (Exception e) {
                    // TODO
                }
            }
        }
        else {
            room.removePlayer(player);
            if (room.getPlayers().isEmpty()) {
                try {
                    mainController.deleteRoom(room.getRoomId());
                } catch (Exception e) {
                    // TODO
                }
            }
        }
    }
}