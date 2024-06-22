package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.actions.Action;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.BlockingQueue;

public class SocketServer {
    private final MainController mainController;
    private final BlockingQueue<Action> actions;
    private ServerSocket listenSocket;

    public SocketServer(BlockingQueue<Action> actions) {
        this.actions = actions;
        this.mainController = MainController.getInstance();
        bind();
        acceptConnections();
    }

    private void bind() {
        try {
            listenSocket = new ServerSocket(DefaultValue.Default_Socket_port);
            System.out.println("Server Socket ready");
        } catch (IOException e) {
            System.out.println("Server Socket not working!");
        }
    }

    public void acceptConnections() {
        Socket clientSocket;
        try {
            while ((clientSocket = listenSocket.accept()) != null) {
                ClientHandler handler = new ClientHandler(mainController, actions, clientSocket);
                new Thread(() -> {
                    try {
                        handler.executeClientMessages();
                    } catch (IOException | ClassNotFoundException ignored) {
                    }
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Server Socket not working!");
        }
    }
}