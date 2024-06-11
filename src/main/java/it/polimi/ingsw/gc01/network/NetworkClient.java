package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.*;

/**
 * Common interface between Socket and RMI clients
 */
public interface NetworkClient {
    /**
     * @return the room id
     */
    String getRoomId();

    /**
     * Create a new game
     */
    void createGame();

    /**
     * Join the indicated game
     *
     * @param roomId of the game to join
     */
    void joinGame(String roomId);

    /**
     * Join the first available game
     */
    void joinFirstGame();

    /**
     * Chose the selected color for the player
     *
     * @param color chosen color
     */
    void chooseColor(PlayerColor color);

    /**
     * Switch the readiness of the player
     */
    void switchReady();

    /**
     * Choose the secret objective
     *
     * @param cardId of the secret objective
     */
    void chooseSecretObjective(int cardId);

    /**
     * Flip the indicated card
     *
     * @param cardId of the card to flip
     */
    void flipCard(int cardId);

    /**
     * Play the card in the selected position
     *
     * @param cardId   of the card to play
     * @param position where to play the card
     */
    void playCard(int cardId, Position position);

    /**
     * Draw the chosen card
     *
     * @param choice index of the chosen card
     */
    void drawCard(int choice);

    /**
     * Leave the current room
     */
    void leave();
}