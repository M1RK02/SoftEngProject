package it.polimi.ingsw.gc01.UI;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.rmi.RmiClient;

import java.rmi.RemoteException;
import java.util.Scanner;

public class TUI extends UI {
    private NetworkClient client;

    public TUI(){
    }

    public void printTitle(){
        System.out.println(
                "\t\t\t\t\t\t\t\t\t ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗\n" +
                "\t\t\t\t\t\t\t\t\t██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝\n" +
                "\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║  ██║█████╗   ╚███╔╝ \n" +
                "\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗ \n" +
                "\t\t\t\t\t\t\t\t\t╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗\n" +
                "\t\t\t\t\t\t\t\t\t╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝\n" +
                "\n" +
                "\t\t\t\t\t\t███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                "\t\t\t\t\t\t████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                "\t\t\t\t\t\t██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                "\t\t\t\t\t\t██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                "\t\t\t\t\t\t██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                "\t\t\t\t\t\t╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n"

        );
    }

    public String askPlayerName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type in the nickname that you want to use to play CODEX NATURALIS (lazzar edidtion)");
        return scanner.nextLine();
    }

    public void askAndSetIP(){
        System.out.println("Type in the Server IP or leave emtpy for localhost:\n");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty()){
            DefaultValue.ServerIp = "localhost";
        } else {
            DefaultValue.ServerIp = input;
        }
    }

    public void askAndSetPort(){
        System.out.println("On which port are you going to connect your computer?\n" );
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty()){
            DefaultValue.RMIPort = 1234;
        } else {
            DefaultValue.RMIPort = Integer.parseInt(input);
        }
    }

    public void createRMIClient(String playerName) throws RemoteException {
        this.client = new RmiClient(playerName, this);;
    }

    public void askModalityToEnterGame() throws RemoteException {
        System.out.println("Would you rather:\n" +
                "(1) Create a new Game\n" +
                "(2) Join the first Available Game\n" +
                "(3) Join a Game by ID");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        switch (input){
            case (1):
                this.client.createGame();
                System.out.println("New game created, waiting for more players...\n");
                break;
            case (2):
                this.client.joinFirstGame();
                System.out.println("Joining the first Available Game\n");
                break;
            case (3):
                System.out.println("Type the roomId of the Game you want to join...\n");
                String roomId = scanner.nextLine();
                this.client.joinGame(roomId);
                System.out.println("Joining the Game...\n");
                break;
        }
    }

    @Override
    public void showRoom(String roomId) {
        System.out.println("Sto mostrando la room che ho joinato");
        System.out.println(roomId);
    }
}