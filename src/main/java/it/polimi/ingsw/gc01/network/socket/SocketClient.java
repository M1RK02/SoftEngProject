package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.socket.messages.CreateGameMessage;
import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.UI;

import java.io.*;
import java.net.Socket;

import static it.polimi.ingsw.gc01.network.socket.SocketMessage.*;


public class SocketClient implements NetworkClient {
    private ObjectInputStream input;

    private ObjectOutputStream output;

    private String roomId;

    private String playerName;

    private UI ui;

    private Socket serverSocket;

    public SocketClient(String playerName, UI ui) {
        this.ui = ui;
        this.playerName = playerName;
        connect();
    }

    private void connect() {
        try {
            serverSocket = new Socket(DefaultValue.ServerIp, DefaultValue.Default_port_Socket);
            ObjectOutputStream outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(serverSocket.getInputStream());
            this.output = outputStream;
            this. input = inputStream;
            System.out.println("Socket client connected");
        } catch (IOException e) {
            System.out.println("Socket server not working");
        }
    }

    /**
     * @return the room id
     */
    @Override
    public String getRoomId() {
        return "";
    }

    /**
     * Create a new game
     */
    @Override
    public void createGame() {
        try {
            output.writeObject(CREATEGAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Join the indicated game
     *
     * @param roomId of the game to join
     */
    @Override
    public void joinGame(String roomId) {

    }

    /**
     * Join the first available game
     */
    @Override
    public void joinFirstGame() {

    }

    /**
     * Chose the selected color for the player
     *
     * @param color chosen color
     */
    @Override
    public void chooseColor(PlayerColor color) {

    }

    /**
     * Switch the readiness of the player
     */
    @Override
    public void switchReady() {

    }

    /**
     * Choose the secret objective
     *
     * @param cardId of the secret objective
     */
    @Override
    public void chooseSecretObjective(int cardId) {

    }

    /**
     * Flip the indicated card
     *
     * @param cardId of the card to flip
     */
    @Override
    public void flipCard(int cardId) {

    }

    /**
     * Play the card in the selected position
     *
     * @param cardId   of the card to play
     * @param position where to play the card
     */
    @Override
    public void playCard(int cardId, Position position) {

    }

    /**
     * Draw the chosen card
     *
     * @param choice index of the chosen card
     */
    @Override
    public void drawCard(int choice) {

    }

    /**
     * Leave the current room
     */
    @Override
    public void leave() {

    }
}
