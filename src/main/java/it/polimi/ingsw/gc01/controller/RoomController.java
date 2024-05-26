package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.cards.StarterCard;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.*;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.util.List;

public class RoomController {
    private MainController mainController;
    private Room room;
    private WaitingRoom waitingRoom;
    private GameState state;

    public RoomController() {
        this.waitingRoom = new WaitingRoom();
        this.state = GameState.WAITING;
        mainController = MainController.getInstance();
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
     * @param client reference to the client
     */
    public void addPlayer(String playerName, VirtualView client) throws PlayerAlreadyInException, MaxPlayersInException{
        if (state != GameState.WAITING) {
            throw new GameInProgressException();
        }
        List<Player> players = waitingRoom.getPlayers();
        if (players.size() + 1 > DefaultValue.MaxNumOfPlayer) {
            throw new MaxPlayersInException();
        }
        if (players.stream().map(Player::getName).noneMatch(x -> x.equals(playerName))) {
            waitingRoom.addPlayer(playerName, client);
        } else {
            throw new PlayerAlreadyInException();
        }
    }


    /**
     * Create the room and gives to all the players 1 Starter Card
     */
    public void prepareGame() {
        ObserverManager notifier = room.getNotifier();
        notifier.showAvailableColor(room.getCurrentPlayer().getName(), waitingRoom.getAvailableColors());
        notifier.showTable(room.getDrawableCards());
        notifier.showCommonObjectives(room.getCommonObjectives());
    }

    /**
     * for each player distribute 2 Resource Card, 1 Golden Card and 2 Objective Cards
     */
    public void distributeCards(){
        ObserverManager notifier = room.getNotifier();
        for (Player p : room.getPlayers()){
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().get(0).setFront(true);
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().get(1).setFront(true);
            p.getHand().add(room.getGoldenDeck().pick());
            p.getHand().get(2).setFront(true);
            p.getPossibleObjectives().add(room.getObjectiveDeck().pick());
            p.getPossibleObjectives().add(room.getObjectiveDeck().pick());
            notifier.showHand(p.getName(), p.getHand());
            notifier.showSecretObjectives(p.getName(), p.getPossibleObjectives());
        }
    }

    /**
     * Starts the game by setting the game state to RUNNING
     */
    private void startGame() {
        this.state = GameState.RUNNING;
        room = new Room(waitingRoom.getRoomId(), waitingRoom.getPlayers(), waitingRoom.getNotifier());
        ObserverManager notifier = room.getNotifier();
        notifier.serviceMessage(DefaultValue.ANSI_PURPLE + "Game is starting!" + DefaultValue.ANSI_RESET);
        giveStarter();
    }

    private void giveStarter() {
        ObserverManager notifier = room.getNotifier();
        Player player = room.getCurrentPlayer();
        notifier.updateCurrentPlayer(player.getName());
        player.getHand().add(room.getStarterDeck().pick());
        notifier.showStarter(player.getName(), (StarterCard) player.getHand().getFirst());
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
    public void endGame (){
        calculateStrategy();
        List<Player> winners = room.getWinners();
        state = GameState.ENDED;
        room.getNotifier().serviceMessage("The winner is "+winners.get(0).getName());
    }


    /**
     *
     * @param playerName the player to set the color
     * @param color the color to set to the player who is choosing
     */
    public void chooseColor(String playerName, PlayerColor color){
        waitingRoom.setColor(playerName, color);
        nextPlayer();
        if(!room.getCurrentPlayer().equals(getPlayers().getFirst())) {
            room.getNotifier().showAvailableColor(room.getCurrentPlayer().getName(), waitingRoom.getAvailableColors());
        } else {
            room.getNotifier().serviceMessage(DefaultValue.ANSI_BLUE + "-> Distributing cards!" + DefaultValue.ANSI_RESET);
        }
    }

    /**
     *
     * @param playerName the player to set ready or unready
     */
    public void switchReady(String playerName){
        waitingRoom.getPlayerByName(playerName).switchReady();
        if (waitingRoom.readyToStart()){
            startGame();
        }
    }

    /**
     * Set the secret objective for a player
     *
     * @param playerName the player that is choosing the objective card
     * @param cardId the objective chosen by the player
     */
    public void chooseSecretObjective(String playerName, int cardId){
        Player player = room.getPlayerByName(playerName);
        ObjectiveCard objective = null;
        for (ObjectiveCard card : player.getPossibleObjectives()) {
            if (card.getId() == cardId) {
                objective = card;
            }
        }
        if (objective != null) {
            player.setSecretObjective(objective);
        }else {
            room.getNotifier().showError(playerName, "No objective found");
        }

    }

    /**
     * Flip a card
     *
     * @param playerName the player that want to flip the card
     * @param cardId the card that needs to be flipped
     */
    public void flipCard (String playerName, int cardId){
        Player player = room.getPlayerByName(playerName);
        PlayableCard card = null;
        for (PlayableCard curr : player.getHand()){
            if (curr.getId() == cardId){
                card = curr;
            }
        }
        if (card != null){
            card.setFront(!card.isFront());
        }else{
            room.getNotifier().showError(playerName, "No card found");
        }
    }

    /**
     * Play a card
     * @param playerName the player who wants to play the card
     * @param cardId the card that needs to be played
     * @param position the position of the player field in which the card needs to be played
     */
    public void playCard (String playerName, int cardId, Position position){
        Player player = room.getPlayerByName(playerName);
        PlayableCard card = null;
        ObserverManager notifier = room.getNotifier();

        if (!room.getCurrentPlayer().equals(player)){
            notifier.showError(playerName, "Its not your turn");
            return;
        }

        for (PlayableCard curr : player.getHand()){
            if (curr.getId() == cardId){
                card = curr;
            }
        }

        if (card != null){
            if (card.isFront()){
                if (card instanceof GoldenCard){
                    if (!((GoldenCard) card).checkRequirements(room.getCurrentPlayer())){
                        notifier.showError(playerName, "You don't have the required items");
                    } else {
                        player.playCard(card, position);
                    }
                } else {
                    player.playCard(card, position);
                }
            } else {
                player.playCard(card, position);
            }
        } else{
            notifier.showError(playerName, "No card found");
        }

        if (getState().equals(GameState.LAST_CIRCLE) && player.equals(room.getPlayers().getLast())){
            endGame();
        }

        if (card instanceof StarterCard){
            nextPlayer();
            player = room.getCurrentPlayer();
            if(!player.equals(getPlayers().getFirst())) {
                player.getHand().add(room.getStarterDeck().pick());
                notifier.showStarter(player.getName(), (StarterCard) player.getHand().getFirst());
            } else {
                prepareGame();
            }

        }
    }

    /**
     * Draw a card from the drawable Cards in the table
     *
     * @param playerName the player who is trying to draw a card
     * @param position the position where the player wants to draw
     */
    public void drawCard(String playerName, TablePosition position){
        Player player = room.getPlayerByName(playerName);
        if (!room.getCurrentPlayer().equals(player)){
            room.getNotifier().showError(playerName, "Its not your turn");
            return;
        }

        if (position.equals(TablePosition.RESOURCEDECK)){
            player.getHand().add(room.getDrawableCards().get(position));
            player.getHand().get(2).setFront(true);
            room.getDrawableCards().remove(position);
            if (!room.getResourceDeck().isEmpty()){
                room.getDrawableCards().put(position, room.getResourceDeck().pick());
            }
        }
        if (position.equals(TablePosition.RESOURCERIGHT) || position.equals(TablePosition.RESOURCELEFT)){
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().remove(position);
            if (!room.getResourceDeck().isEmpty()){
                room.getDrawableCards().put(position, room.getResourceDeck().pick());
                room.getDrawableCards().get(position).setFront(true);
            }
        }
        if (position.equals(TablePosition.GOLDENDECK)){
            player.getHand().add(room.getDrawableCards().get(position));
            player.getHand().get(2).setFront(true);
            room.getDrawableCards().remove(position);
            if (!room.getGoldenDeck().isEmpty()){
                room.getDrawableCards().put(position, room.getGoldenDeck().pick());
            }
        }
        if (position.equals(TablePosition.GOLDENRIGHT) || position.equals(TablePosition.GOLDENLEFT)){
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().remove(position);
            if (!room.getGoldenDeck().isEmpty()){
                room.getDrawableCards().put(position, room.getGoldenDeck().pick());
                room.getDrawableCards().get(position).setFront(true);
            }
        }

        if (room.getResourceDeck().isEmpty() && room.getGoldenDeck().isEmpty() && room.getPlayers().get(room.getPlayers().size() - 1).equals(player)){
            setState(GameState.LAST_CIRCLE);
        }

        nextPlayer();
    }

    /**
     * Leave a player from a game
     *
     * @param playerName the player who wants to leave
     */
    public void leave(String playerName){
        if (room == null) {
            waitingRoom.removePlayer(waitingRoom.getPlayerByName(playerName));
            if (waitingRoom.getPlayers().isEmpty()) {
                mainController.deleteRoom(waitingRoom.getRoomId());
            }
        }else {
            room.removePlayer(room.getPlayerByName(playerName));
            if (room.getPlayers().isEmpty()) {
                mainController.deleteRoom(room.getRoomId());
            }
        }
    }
}