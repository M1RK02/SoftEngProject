package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.player.Position;

import java.util.*;

public class FieldUtil {
    private final List<Integer> cards;
    private final Map<Integer, Boolean> side;
    private final Map<Integer, Position> field;
    private final ClientDeck clientDeck;

    public FieldUtil(){
        cards = new ArrayList<>();
        side = new HashMap<>();
        field = new HashMap<>();
        clientDeck = new ClientDeck();
    }

    public void playCard(int id, int x, int y, boolean front) {
        cards.add(id);
        side.put(id, front);
        field.put(id, new Position(x, y));
    }

    public void printUsedField() {
        int maxX = field.values().stream().mapToInt(Position::getX).max().orElse(1624);
        int maxY = field.values().stream().mapToInt(Position::getY).max().orElse(203);
        int minX = field.values().stream().mapToInt(Position::getX).min().orElse(0);
        int minY = field.values().stream().mapToInt(Position::getY).min().orElse(0);

        maxX = realX(maxX);
        maxY = realY(maxY);
        minX = realX(minX);
        minY = realY(minY);

        String [] fieldToPrint = generateField();
        for(int i = maxY; i < minY+7; i++) {
            System.out.println(fieldToPrint[i].substring(minX, maxX+25));
        }
    }

    private String[] generateField() {
        String[] printableField = new String[204];
        Arrays.fill(printableField, new String(new char[1625]).replace('\u0000', ' '));

        for (Integer i : cards) {
            String[] card = clientDeck.generateCardById(i, side.get(i));
            for (int j = 0; j < card.length; j++) {
                String riga = printableField[realY(field.get(i).getY())+j];
                printableField[realY(field.get(i).getY())+j] = riga.substring(0, realX(field.get(i).getX())) + card[j] + riga.substring(realX(field.get(i).getX())+25, 1625);
            }
        }

        return printableField;
    }

    private int realX (int x) {
        return (40*20) + x*20;
    }

    private int realY (int y) {
        return (8*20) - y*4;
    }
}