package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.cards.CardColor;
import it.polimi.ingsw.gc01.model.cards.StarterCard;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import it.polimi.ingsw.gc01.model.player.Position;

import java.util.*;

// QUESTO È UN PROOF OF CONCEPT NON PER FORZA LO FACCIAMO COSÌ
public class CardPrinting {

    private static char[][] generateCardById(int id) {
        // VA LETTO DAL JSON E NON FATTO COSI
        StarterDeck deck = new StarterDeck();
        StarterCard card = deck.pickById(id);

        // CARTA VUOTA
        String[] colors = new String[5];
        for (int i = 0; i < 4; i++) {
            colors[i] = CardColor.values()[i].toString();
        }
        colors[4] = "GAY";
        String color = colors[new Random().nextInt(colors.length)];
        color = color.substring(0, 3);
        char[][] carta = new char[7][25];
        carta[0] = "╔═══╦═══════════════╦═══╗".toCharArray();
        carta[1] = ("║   ║      "+color+"      ║   ║").toCharArray();
        carta[2] = "╠═══╝     ╔═══╗     ╚═══╣".toCharArray();
        carta[3] = "║         ║   ║         ║".toCharArray();
        carta[4] = "╠═══╗     ╚═══╝     ╔═══╣".toCharArray();
        carta[5] = "║   ║               ║   ║".toCharArray();
        carta[6] = "╚═══╩═══════════════╩═══╝".toCharArray();

        // SIMBOLI CENTRALI
        switch (card.getCenterResources().size()) {
            case 1 -> {
                carta[3][12] = card.getCenterResources().toArray()[0].toString().charAt(0);
            }
            case 2 -> {
                carta[3][11] = card.getCenterResources().toArray()[0].toString().charAt(0);
                carta[3][13] = card.getCenterResources().toArray()[1].toString().charAt(0);
            }
            case 3 -> {
                carta[3][11] = card.getCenterResources().toArray()[0].toString().charAt(0);
                carta[3][12] = card.getCenterResources().toArray()[1].toString().charAt(0);
                carta[3][13] = card.getCenterResources().toArray()[2].toString().charAt(0);
            }
        }

        // SIMBOLI AI CORNER
        char x = ' ';
        String topLeft = card.getCorners().get(CornerPosition.TOP_LEFT).getResource().toString();
        switch (topLeft) {
            case "EMPTY" -> x = ' ';
            case "FULL" -> {
                carta[0][4] = '═';
                carta[1][4] = ' ';
                carta[2][4] = ' ';
                carta[2][3] = ' ';
                carta[2][2] = ' ';
                carta[2][1] = ' ';
                carta[2][0] = '║';
            }
            default -> x = topLeft.charAt(0);
        }
        carta[1][2] = x;

        String bottomLeft = card.getCorners().get(CornerPosition.BOTTOM_LEFT).getResource().toString();
        switch (bottomLeft) {
            case "EMPTY" -> x = ' ';
            case "FULL" -> {
                carta[4][0] = '║';
                carta[4][1] = ' ';
                carta[4][2] = ' ';
                carta[4][3] = ' ';
                carta[4][4] = ' ';
                carta[5][4] = ' ';
                carta[6][4] = '═';
            }
            default -> x = bottomLeft.charAt(0);
        }
        carta[5][2] = x;

        String topRight = card.getCorners().get(CornerPosition.TOP_RIGHT).getResource().toString();
        switch (topRight) {
            case "EMPTY" -> x = ' ';
            case "FULL" -> {
                carta[0][20] = '═';
                carta[1][20] = ' ';
                carta[2][20] = ' ';
                carta[2][21] = ' ';
                carta[2][22] = ' ';
                carta[2][23] = ' ';
                carta[2][24] = '║';
            }
            default -> x = topRight.charAt(0);
        }
        carta[1][22] = x;

        String bottomRight = card.getCorners().get(CornerPosition.BOTTOM_RIGHT).getResource().toString();
        switch (bottomRight) {
            case "EMPTY" -> x = ' ';
            case "FULL" -> {
                carta[4][24] = '║';
                carta[4][23] = ' ';
                carta[4][22] = ' ';
                carta[4][21] = ' ';
                carta[4][20] = ' ';
                carta[5][20] = ' ';
                carta[6][20] = '═';
            }
            default -> x = bottomRight.charAt(0);
        }
        carta[5][22] = x;

        return carta;
    }

    private static int realX (int x) {
        return (40*20) + x*20;
    }

    private static int realY (int y) {
        return (8*20) - y*4;
    }

    public static void main(String[] args){
        List<Integer> playedCard = new ArrayList<>();
        playedCard.add(81);
        playedCard.add(82);
        playedCard.add(83);
        playedCard.add(84);
        playedCard.add(85);
        playedCard.add(86);

        Map<Integer, Position> field = new HashMap<Integer, Position>();
        field.put(81, new Position(0,0));
        field.put(82, new Position(-1,1));
        field.put(83, new Position(1,1));
        field.put(84, new Position(0,2));
        field.put(85, new Position(-2,2));
        field.put(86, new Position(2, 2));

        String[] printableField = new String[204];
        for (int i = 0; i < printableField.length; i++) {
            printableField[i] = new String(new char[1625]).replace('\u0000', ' ');
        }

        for (Integer i : playedCard) {
            char[][] card = generateCardById(i);
            for (int j = 0; j < card.length; j++) {
                String riga = printableField[realY(field.get(i).getY())+j];
                printableField[realY(field.get(i).getY())+j] = riga.substring(0, realX(field.get(i).getX())) + String.valueOf(card[j]) + riga.substring(realX(field.get(i).getX())+25, 1625);
            }
        }

        int maxX = field.values().stream().mapToInt(Position::getX).max().orElse(1624);
        int maxY = field.values().stream().mapToInt(Position::getY).max().orElse(203);
        int minX = field.values().stream().mapToInt(Position::getX).min().orElse(0);
        int minY = field.values().stream().mapToInt(Position::getY).min().orElse(0);

        maxX = realX(maxX);
        maxY = realY(maxY);
        minX = realX(minX);
        minY = realY(minY);

        for(int i = maxY; i < minY+7; i++) {
            System.out.println(printableField[i].substring(minX, maxX+25));
        }
    }
}