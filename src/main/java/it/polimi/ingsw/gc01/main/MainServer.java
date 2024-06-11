package it.polimi.ingsw.gc01.main;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.network.rmi.RmiServer;

import java.util.*;

/**
 * Executable class for the server
 */
public class MainServer {

    /**
     * Main method, it will launch both RMI and Socket servers
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
        new RmiServer();
    }


    /**
     * Check if the input is a valid IP address
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
}