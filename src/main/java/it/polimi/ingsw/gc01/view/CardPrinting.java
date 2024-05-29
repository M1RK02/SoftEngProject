package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.Position;

import java.util.*;

public class CardPrinting {
    private final List<Integer> playedCard;
    private final Map<Integer, Boolean> side;
    private final Map<Integer, Position> field;
    private final ClientDeck clientDeck;

    private CardPrinting(){
        playedCard = new ArrayList<>();
        side = new HashMap<>();
        field = new HashMap<>();
        clientDeck = new ClientDeck();
    }

    private void playCard(int id, int x, int y, boolean front) {
        playedCard.add(id);
        side.put(id, front);
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
            String[] card = clientDeck.generateCardById(i, side.get(i));
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
            String[] card = clientDeck.generateCardById(i, true);
            for(String line : card) {
                System.out.println(line);
            };
        }
    }

    private void printAllStarterCards(){
        for (int i = 81; i <= 86; i++) {
            System.out.println("       cardID = " + i);
            String[] cardFront = clientDeck.generateCardById(i, true);
            String[] cardBack = clientDeck.generateCardById(i, false);
            for (int j = 0; j < cardFront.length; j++){
                System.out.print(DefaultValue.ANSI_YELLOW + cardFront[j] + "\t\t");
                System.out.println(cardBack[j] + DefaultValue.ANSI_RESET);
            }
        }
    }

    private void printAllGoldenCards(){
        for (int i = 41; i <= 80; i++) {
            System.out.println("       cardID = " + i);
            String[] cardFront = clientDeck.generateCardById(i, true);
            String[] cardBack = clientDeck.generateCardById(i, false);
            for (int j = 0; j < cardFront.length; j++){
                System.out.print(cardFront[j] + "\t\t");
                System.out.println(cardBack[j]);
            }
        }
    }

    private void printAllResourceCards(){
        for (int i = 1; i <= 40; i++) {
            System.out.println("       cardID = " + i);
            String[] cardFront = clientDeck.generateCardById(i, true);
            String[] cardBack = clientDeck.generateCardById(i, false);
            for (int j = 0; j < cardFront.length; j++){
                System.out.print(cardFront[j] + "\t\t");
                System.out.println(cardBack[j]);
            }
        }
    }

    public void test(){
        playCard(82, 0, 0, false);
        playCard(19, -1, 1, false);
        playCard(3, 1, 1, true);
        playCard(70, 0, 2, true);
        playCard(5, -2, 2, true);
        playCard(6, 2, 2, false);
        playCard(50, 1, -1, true);
        playCard(30, 0, -2, true);
        playCard(42, -1, -1, true);

        String[] fieldToPrint = generateField();

        //printAllField(fieldToPrint);

        //printTotalFieldPrend();

        printUsedField(fieldToPrint);

        printAllObjectiveCards();

        printAllStarterCards();

        printAllGoldenCards();

        printAllResourceCards();
    }

    public static void main(String[] args) {
        new CardPrinting().test();
    }
}