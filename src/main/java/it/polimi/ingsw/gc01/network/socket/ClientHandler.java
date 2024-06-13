package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.*;

import java.io.*;
import java.net.Socket;

public class ClientHandler /* implements VirtualView */ {
    private final MainController mainController;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private RoomController room;

    public ClientHandler(Socket clientSocket) {
        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ignored) {
        }
        mainController = MainController.getInstance();
    }

    public void executeMessages() {
        //TODO LEGGE INPUT ED ESEGUE IL MESSAGGIO SUL CONTROLLER
    }

    //TODO METODI DELLA VIRTUALVIEW CHE MANDANO IL MESSAGGIO AL CLIENT
}