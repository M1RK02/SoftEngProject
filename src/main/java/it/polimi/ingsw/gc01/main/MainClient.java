package it.polimi.ingsw.gc01.main;

import it.polimi.ingsw.gc01.UI.TUI;

import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) throws RemoteException {
        printTitle();
        switch (askUI()) {
            case 1 -> new TUI();
            case 2 -> System.out.println("Chosen GUI");
        }
    }


    private static void printTitle(){
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

    private static int askUI(){
        System.out.println("Select user interface\n(1) TUI\n(2) GUI");
        do {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try{
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2){
                    return choice;
                }
                System.out.println("Wrong choice\n");
            } catch (NumberFormatException e){
                System.out.println("Wrong format\n");
            }
        } while (true);
    }
}