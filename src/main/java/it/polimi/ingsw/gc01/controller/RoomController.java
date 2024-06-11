package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.*;
import it.polimi.ingsw.gc01.network.*;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.util.*;

public class RoomController {
    private final MainController mainController;
    private final WaitingRoom waitingRoom;
    private Room room;
    private GameState state;

    public RoomController() {
        mainController = MainController.getInstance();
        waitingRoom = new WaitingRoom();
        state = GameState.WAITING;
    }

    /**
     * @return the Room of the started game
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @return the waitingRoom of the game waiting for starting
     */
    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    /**
     * @return the state of the game
     */
    public GameState getState() {
        return state;
    }

    /**
     * @param state set the state of the game
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * @return the roomId associated to the controller
     */
    public String getRoomId() {
        return waitingRoom.getRoomId();
    }

    /**
     * @param playerName name of the players that needs to be added to the WaitingRoom
     * @param client     reference to the client
     */
    public void addPlayer(String playerName, VirtualView client) throws PlayerAlreadyInException, MaxPlayersInException {
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
     * @param playerName the player to set ready or unready
     */
    public void switchReady(String playerName) {
        waitingRoom.getPlayerByName(playerName).switchReady();
        if (waitingRoom.readyToStart()) {
            prepareGame();
        }
    }

    private void prepareGame() {
        room = new Room(waitingRoom.getRoomId(), waitingRoom.getPlayers(), waitingRoom.getNotifier());
        state = GameState.STARTER_SELECTION;
        giveStarter();
    }

    private void giveStarter() {
        Player currentPlayer = room.getCurrentPlayer();
        currentPlayer.getHand().add(room.getStarterDeck().pick());
        currentPlayer.getHand().get(0).setFront(true);
        ObserverManager notifier = room.getNotifier();
        notifier.showStarter(currentPlayer.getName(), (StarterCard) currentPlayer.getHand().getFirst());
    }

    private void showAvailableColors() {
        Player currentPlayer = room.getCurrentPlayer();
        ObserverManager notifier = room.getNotifier();
        notifier.showAvailableColor(currentPlayer.getName(), room.getAvailableColors());
    }

    private void showObjective() {
        Player currentPlayer = room.getCurrentPlayer();
        ObserverManager notifier = room.getNotifier();
        notifier.showSecretObjectives(currentPlayer.getName(), currentPlayer.getPossibleObjectives());
    }

    private void showField() {
        Player currentPlayer = room.getCurrentPlayer();
        ObserverManager notifier = room.getNotifier();
        notifier.showField(currentPlayer.getName());
    }

    private void showPoints() {
        ObserverManager notifier = room.getNotifier();
        Map<String, Integer> points = new HashMap<>();
        for (Player player : room.getPlayers()) {
            points.put(player.getName(), player.getPoints());
        }
        notifier.showPoints(points);
    }

    private void showHand() {
        Player currentPlayer = room.getCurrentPlayer();
        ObserverManager notifier = room.getNotifier();
        notifier.showHand(currentPlayer.getName(), currentPlayer.getHand());
    }

    /**
     * Change the currentPlayer to the next Player in the circle checking the current state
     */
    public void nextPlayer() {
        ObserverManager notifier = room.getNotifier();
        room.setCurrentPlayer(room.getNextPlayer());
        Player currentPlayer = room.getCurrentPlayer();
        switch (state) {
            case STARTER_SELECTION:
                if (!currentPlayer.equals(room.getPlayers().getFirst())) {
                    giveStarter();
                } else {
                    state = GameState.COLOR_SELECTION;
                    showAvailableColors();
                }
                break;
            case COLOR_SELECTION:
                if (!currentPlayer.equals(room.getPlayers().getFirst())) {
                    showAvailableColors();
                } else {
                    state = GameState.OBJECTIVE_SELECTION;
                    distributeCards();
                    showObjective();
                }
                break;
            case OBJECTIVE_SELECTION:
                if (!currentPlayer.equals(room.getPlayers().getFirst())) {
                    showObjective();
                } else {
                    state = GameState.RUNNING;
                    showPoints();
                    showField();
                    showHand();
                }
                break;
            case RUNNING:
                if (currentPlayer.equals(room.getPlayers().getFirst())) {
                    if (hasOneReachedTwenty() || areDecksEmpty()) {
                        state = GameState.LAST_CIRCLE;
                        notifier.showLastCircle();
                    }
                }
                showPoints();
                showField();
                showHand();
                break;
            case LAST_CIRCLE:
                if (!currentPlayer.equals(room.getPlayers().getFirst())) {
                    showPoints();
                    showField();
                    showHand();
                } else {
                    state = GameState.ENDED;
                    endGame();
                }
        }
    }

    /**
     * @param playerName the player to set the color
     * @param color      the color to set to the player who is choosing
     */
    public void chooseColor(String playerName, PlayerColor color) {
        room.getPlayerByName(playerName).setColor(color);
        room.getAvailableColors().remove(color);
        nextPlayer();
    }

    /**
     * for each player distribute 2 Resource Card, 1 Golden Card and 2 Objective Cards
     */
    private void distributeCards() {
        room.getNotifier().showCommonObjectives(room.getCommonObjectives());
        for (Player p : room.getPlayers()) {
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().get(0).setFront(true);
            p.getHand().add(room.getResourceDeck().pick());
            p.getHand().get(1).setFront(true);
            p.getHand().add(room.getGoldenDeck().pick());
            p.getHand().get(2).setFront(true);
            p.getPossibleObjectives().add(room.getObjectiveDeck().pick());
            p.getPossibleObjectives().add(room.getObjectiveDeck().pick());
        }
    }

    /**
     * Calculate all the points done by the strategies (both Common and the Secret one) for every player
     */
    private void calculateStrategy() {
        List<Player> players = room.getPlayers();
        List<ObjectiveCard> commonObjectives = room.getCommonObjectives();
        for (Player p : players) {
            p.addObjectivePoints(commonObjectives.get(0).calculatePoints(p));
            p.addObjectivePoints(commonObjectives.get(1).calculatePoints(p));
            p.addObjectivePoints(p.getSecretObjective().calculatePoints(p));
        }
    }

    /**
     * @return true if a player has reached 20 points
     */
    private boolean hasOneReachedTwenty() {
        for (Player p : room.getPlayers()) {
            if (p.getPoints() >= DefaultValue.PointsToFinish) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if both decks are empty
     */
    private boolean areDecksEmpty() {
        return room.getResourceDeck().isEmpty() && room.getGoldenDeck().isEmpty();
    }

    /**
     * Calculates all the points done by players, decrees a winner and set the game state to Ended
     */
    public void endGame() {
        calculateStrategy();
        List<String> winners = room.getWinners().stream().map(Player::getName).toList();
        ObserverManager notifier = room.getNotifier();
        notifier.showWinners(winners);
        notifier.backToMenu();
    }

    /**
     * Set the secret objective for a player
     *
     * @param playerName the player that is choosing the objective card
     * @param cardId     the objective chosen by the player
     */
    public void chooseSecretObjective(String playerName, int cardId) {
        Player player = room.getPlayerByName(playerName);
        ObjectiveCard objective = null;
        for (ObjectiveCard card : player.getPossibleObjectives()) {
            if (card.getId() == cardId) {
                objective = card;
            }
        }
        if (objective != null) {
            player.setSecretObjective(objective);
            nextPlayer();
        } else {
            room.getNotifier().showError(playerName, "No objective found");
        }
    }

    /**
     * Flip a card
     *
     * @param playerName the player that want to flip the card
     * @param cardId     the card that needs to be flipped
     */
    public void flipCard(String playerName, int cardId) {
        Player player = room.getPlayerByName(playerName);
        PlayableCard card = null;
        for (PlayableCard curr : player.getHand()) {
            if (curr.getId() == cardId) {
                card = curr;
            }
        }
        if (card != null) {
            card.setFront(!card.isFront());
        } else {
            room.getNotifier().showError(playerName, "No card found");
        }
    }

    /**
     * Play a card
     *
     * @param playerName the player who wants to play the card
     * @param cardId     the card that needs to be played
     * @param position   the position of the player field in which the card needs to be played
     */
    public void playCard(String playerName, int cardId, Position position) {
        Player player = room.getPlayerByName(playerName);
        PlayableCard card = null;
        ObserverManager notifier = room.getNotifier();

        for (PlayableCard curr : player.getHand()) {
            if (curr.getId() == cardId) {
                card = curr;
            }
        }

        if (card != null) {
            if (card.isFront()) {
                if (card instanceof GoldenCard) {
                    if (!((GoldenCard) card).checkRequirements(room.getCurrentPlayer())) {
                        notifier.showError(playerName, "PLAY You don't have the required items");
                        return;
                    } else {
                        player.playCard(card, position);
                    }
                } else {
                    player.playCard(card, position);
                }
            } else {
                player.playCard(card, position);
            }
        } else {
            notifier.showError(playerName, "No card found");
        }

        if (card instanceof StarterCard || state.equals(GameState.LAST_CIRCLE)) {
            nextPlayer();
        } else {
            notifier.showTable(playerName, room.getDrawableCards());
        }
    }

    /**
     * Draw a card from the drawable Cards in the table
     *
     * @param playerName the player who is trying to draw a card
     * @param position   the position where the player wants to draw
     */
    public void drawCard(String playerName, TablePosition position) {
        Player player = room.getPlayerByName(playerName);
        ObserverManager notifier = room.getNotifier();

        if (room.getDrawableCards().get(position) == null) {
            notifier.showError(playerName, "DRAW Invalid position");
            return;
        }

        if (position.equals(TablePosition.RESOURCEDECK)) {
            player.getHand().add(room.getDrawableCards().get(position));
            player.getHand().get(2).setFront(true);
            room.getDrawableCards().remove(position);
            if (!room.getResourceDeck().isEmpty()) {
                room.getDrawableCards().put(position, room.getResourceDeck().pick());
            }
        }
        if (position.equals(TablePosition.RESOURCERIGHT) || position.equals(TablePosition.RESOURCELEFT)) {
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().remove(position);
            if (!room.getResourceDeck().isEmpty()) {
                room.getDrawableCards().put(position, room.getResourceDeck().pick());
                room.getDrawableCards().get(position).setFront(true);
            }
        }
        if (position.equals(TablePosition.GOLDENDECK)) {
            player.getHand().add(room.getDrawableCards().get(position));
            player.getHand().get(2).setFront(true);
            room.getDrawableCards().remove(position);
            if (!room.getGoldenDeck().isEmpty()) {
                room.getDrawableCards().put(position, room.getGoldenDeck().pick());
            }
        }
        if (position.equals(TablePosition.GOLDENRIGHT) || position.equals(TablePosition.GOLDENLEFT)) {
            player.getHand().add(room.getDrawableCards().get(position));
            room.getDrawableCards().remove(position);
            if (!room.getGoldenDeck().isEmpty()) {
                room.getDrawableCards().put(position, room.getGoldenDeck().pick());
                room.getDrawableCards().get(position).setFront(true);
            }
        }

        nextPlayer();
    }

    /**
     * Leave a player from a game
     *
     * @param playerName the player who wants to leave
     */
    public void leave(String playerName) {
        if (room == null) {
            waitingRoom.removePlayer(playerName);
            if (waitingRoom.getPlayers().isEmpty()) {
                mainController.deleteRoom(waitingRoom.getRoomId());
            }
        } else {
            room.removePlayer(room.getPlayerByName(playerName));
            if (room.getPlayers().isEmpty()) {
                mainController.deleteRoom(room.getRoomId());
            }
        }
    }
}