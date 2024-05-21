package it.polimi.ingsw.gc01.main;

public class MainClient {

    public void printTitle(){
        System.out.println("\t\t\t\t\t\t\t\t\t ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗\n" +
                          "\t\t\t\t\t\t\t\t\t██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝\n" +
                          "\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║  ██║█████╗   ╚███╔╝ \n" +
                          "\t\t\t\t\t\t\t\t\t██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗ \n" +
                          "\t\t\t\t\t\t\t\t\t╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗\n" +
                          " \t\t\t\t\t\t\t\t\t╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝\n" +
                "                                         \n" +
                "\t\t\t\t\t\t███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗\n" +
                "\t\t\t\t\t\t████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝\n" +
                "\t\t\t\t\t\t██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗\n" +
                "\t\t\t\t\t\t██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║\n" +
                "\t\t\t\t\t\t██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║\n" +
                "\t\t\t\t\t\t╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝\n"

                );
    }

 public void askIP(){
        System.out.println("Write Remote IP to connect the server");

 }




    public static void main(String[] args) {
    MainClient client = new MainClient();
    //RmiClient rmiClient = new RmiClient();
    client.printTitle();





    }
    }

