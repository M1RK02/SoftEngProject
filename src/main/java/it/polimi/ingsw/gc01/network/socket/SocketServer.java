package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.rmi.VirtualServer;
import it.polimi.ingsw.gc01.network.rmi.actions.Action;
import it.polimi.ingsw.gc01.network.socket.messages.ClientToServerMessage;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class SocketServer {
    private ServerSocket listenSocket;
    private MainController mainController;
    private BlockingQueue<Action> actions;

    public SocketServer(BlockingQueue<Action> actions) {
        this.actions = actions;
        bind();
        acceptConnections();
    }

    private void bind() {
        try {
            listenSocket = new ServerSocket(DefaultValue.Default_port_Socket);
            System.out.println("Server Socket ready");
        } catch (IOException e) {
            System.out.println("Server Socket not working!");
        }
    }

    public void acceptConnections() {
        Socket clientSocket;
        try {
            while ((clientSocket = listenSocket.accept()) != null) {
                ClientHandler handler = new ClientHandler(actions, clientSocket);
                new Thread(handler::executeMessages).start();
            }
        } catch (IOException e) {
            System.out.println("Server Socket not working!");
        }
    }
}