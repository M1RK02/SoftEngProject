package it.polimi.ingsw.gc01.main;

import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.tui.TUI;
import it.polimi.ingsw.gc01.view.gui.GUI;

import java.util.Scanner;

/**
 * Executable class for the client
 */
public class MainClient {

    /**
     * Main method, it will launch the selected UI
     *
     * @param args (ignored)
     */
    public static void main(String[] args) {
        printTitle();
        switch (askUI()) {
            case 1 -> {
                System.out.println("\nStarting TUI...\n");
                new TUI();
            }
            case 2 -> {
                System.out.println("\nStarting GUI...\n");
                new GUI();
            }
        }
    }

    /**
     * Print the title
     */
    private static void printTitle() {
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

    /**
     * Present the user with the UI choice
     *
     * @return 1 for TUI, 2 for GUI
     */
    private static int askUI() {
        System.out.println("Select user interface:\n(1) TUI\n(2) GUI");
        do {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    return choice;
                }
                System.out.println(DefaultValue.ANSI_YELLOW + "Wrong choice!" + DefaultValue.ANSI_RESET);
            } catch (NumberFormatException e) {
                System.out.println(DefaultValue.ANSI_RED + "Wrong format!" + DefaultValue.ANSI_RESET);
            }
        } while (true);
    }
}