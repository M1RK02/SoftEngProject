package it.polimi.ingsw.gc01.main;

import it.polimi.ingsw.gc01.network.actions.Action;
import it.polimi.ingsw.gc01.network.rmi.RmiServer;
import it.polimi.ingsw.gc01.network.socket.SocketServer;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.util.*;
import java.util.concurrent.*;

/**
 * Executable class for the server
 */
public class MainServer {

    private static BlockingQueue<Action> actions;

    /**
     * Main method, it will launch both RMI and Socket servers
     *
     * @param args (ignored)
     */
    public static void main(String[] args) {
        String input;

        do {
            System.out.println("Type in the Remote IP or leave empty for localhost:");
            input = new Scanner(System.in).nextLine();
        } while (!input.isEmpty() && !isValidIP(input));

        if (!input.isEmpty()) {
            DefaultValue.ServerIp = input;
        }

        System.setProperty("java.rmi.server.hostname", DefaultValue.ServerIp);
        System.setProperty("java.socket.server.hostname", DefaultValue.ServerIp);

        actions = new ArrayBlockingQueue<Action>(100);
        new RmiServer(actions);
        new Thread(() -> new SocketServer(actions)).start();
        executeActions();
    }


    /**
     * Check if the input is a valid IP address
     *
     * @param input string to check
     * @return true if is a valid IP, otherwise false
     */
    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * A thread is created to take actions from the Queue and call their execute method that
     * is going to modify the model of the game
     */
    private static void executeActions() {
        new Thread(() -> {
            try {
                while (true) {
                    //Spila le action e le esegue
                    Action action = actions.take();
                    action.execute();
                }
            } catch (InterruptedException e) {
                System.err.println("Il thread che esegue le Action è stato interrotto");
            }
        }).start();
    }
}