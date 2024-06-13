package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.io.IOException;
import java.net.*;

//TODO Maybe it's working
public class SocketServer {
    private ServerSocket listenSocket;

    public SocketServer() {
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
                ClientHandler handler = new ClientHandler(clientSocket);
                new Thread(handler::executeMessages).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}