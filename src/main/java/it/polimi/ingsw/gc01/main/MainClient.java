package it.polimi.ingsw.gc01.main;

import it.polimi.ingsw.gc01.view.TUI;

import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        printTitle();
        switch (askUI()) {
            case 1: System.out.println("Starting TUI...\n");
                    new TUI();
                    break;
            case 2: System.out.println("Starting GUI...");
                    //new GUI();
                    break;
        }
    }

    private static void printTitle(){
        System.out.println(
                "\t\t\t\t\t\t\t\t\t ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗\n" +
                "\t\t\t\t\t\t\t\t\t██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝\n" +
                "\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║  ██║█████╗   ╚███╔╝ \n" +
                "\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗ \n" +
                "\t\t\t\t\t\t\t\t\t╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗\n" +
                "\t\t\t\t\t\t\t\t\t╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝   ╚═╝\n" +
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
                System.out.println("Wrong choice");
            } catch (NumberFormatException e){
                System.out.println("Wrong format");
            }
        } while (true);
    }
}