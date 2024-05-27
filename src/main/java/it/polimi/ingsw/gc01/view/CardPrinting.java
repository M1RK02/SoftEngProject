package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.player.Position;

import java.util.*;

public class CardPrinting {
    private final List<Integer> playedCard;
    private final Map<Integer, Position> field;
    private final ClientDeck clientDeck;

    private CardPrinting(){
        playedCard = new ArrayList<>();
        field = new HashMap<Integer, Position>();
        clientDeck = new ClientDeck();
    }

    private void playCard(int id, int x, int y) {
        playedCard.add(id);
        field.put(id, new Position(x, y));
    }

    private int realX (int x) {
        return (40*20) + x*20;
    }

    private int realY (int y) {
        return (8*20) - y*4;
    }

    private String[] generateField() {
        String[] printableField = new String[204];
        Arrays.fill(printableField, new String(new char[1625]).replace('\u0000', ' '));

        for (Integer i : playedCard) {
            String[] card = clientDeck.generateCardById(i);
            for (int j = 0; j < card.length; j++) {
                String riga = printableField[realY(field.get(i).getY())+j];
                printableField[realY(field.get(i).getY())+j] = riga.substring(0, realX(field.get(i).getX())) + card[j] + riga.substring(realX(field.get(i).getX())+25, 1625);
            }
        }

        return printableField;
    }

    private void printAllField(String[] fieldToPrint) {
        for (String s : fieldToPrint) {
            System.out.println(s);
        }
    }

    private void printTotalFieldPrend() {
        System.out.println("""
            ╔═══╦═══════════════╦═══╗               ╔═══╦═══════════════╦═══╗               ╔═══╦═══════════════╦═══╗               ╔═══╦═══════════════╦═══╗
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╠═══╝     ╔═══╗     ╚═══╣               ╠═══╝     ╔═══╗     ╚═══╣               ╠═══╝     ╔═══╗     ╚═══╣               ╠═══╝     ╔═══╗     ╚═══╣
            ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║
            ╠═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╣
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╚═══╩═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╩═══╝
                                ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║
            ╔═══╦═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╦═══╗
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╠═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╣
            ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║
            ╠═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╣
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╚═══╩═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╩═══╝
                                ║         ║ ▓ ║         ║               ║         ║SSS║         ║               ║         ║ ▓ ║         ║
            ╔═══╦═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╦═══╗
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╠═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╣
            ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║
            ╠═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╣
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╚═══╩═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╩═══╝
                                ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║
            ╔═══╦═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╬═══╗     ╚═══╝     ╔═══╬═══════════════╦═══╗
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╠═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╬═══════════════╬═══╝     ╔═══╗     ╚═══╣
            ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║               ║         ║ ▓ ║         ║
            ╠═══╗     ╚═══╝     ╔═══╣               ╠═══╗     ╚═══╝     ╔═══╣               ╠═══╗     ╚═══╝     ╔═══╣               ╠═══╗     ╚═══╝     ╔═══╣
            ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║               ║ ▓ ║
            ╚═══╩═══════════════╩═══╝               ╚═══╩═══════════════╩═══╝               ╚═══╩═══════════════╩═══╝               ╚═══╩═══════════════╩═══╝
        """);
    }

    private void printUsedField(String[] fieldToPrint) {
        int maxX = field.values().stream().mapToInt(Position::getX).max().orElse(1624);
        int maxY = field.values().stream().mapToInt(Position::getY).max().orElse(203);
        int minX = field.values().stream().mapToInt(Position::getX).min().orElse(0);
        int minY = field.values().stream().mapToInt(Position::getY).min().orElse(0);

        maxX = realX(maxX);
        maxY = realY(maxY);
        minX = realX(minX);
        minY = realY(minY);

        for(int i = maxY; i < minY+7; i++) {
            System.out.println(fieldToPrint[i].substring(minX, maxX+25));
        }
    }

    private void printAllObjectiveCards(){
        for (int i = 87; i <= 102; i++) {
            System.out.println("       cardID = " + i);
            String[] card = clientDeck.generateCardById(i);
            for(String line : card) {
                System.out.println(line);
            };
        }
    }

    private void printAllStarterCards(){
        for (int i = 81; i <= 86; i++) {
            System.out.println("       cardID = " + i);
            String[] card = clientDeck.generateCardById(i);
            for(String line : card) {
                System.out.println(line);
            }
        }
    }

    public void test(){
        playCard(1, 0, 0);
        playCard(2, -1, 1);
        playCard(3, 1, 1);
        playCard(4, 0, 2);
        playCard(5, -2, 2);
        playCard(6, 2, 2);

        String[] fieldToPrint = generateField();

        //printAllField(fieldToPrint);

        //printTotalFieldPrend();

        printUsedField(fieldToPrint);

        printAllObjectiveCards();

        printAllStarterCards();
    }

    public static void main(String[] args) {
        new CardPrinting().test();
    }
}