package it.polimi.ingsw.gc01.view.tui;

import it.polimi.ingsw.gc01.model.player.Position;

import java.util.*;

import static java.util.stream.Stream.concat;

public class ClientField {
    private final List<Integer> cards;
    private final Map<Integer, Boolean> side;
    private final Map<Integer, Position> field;
    private final ClientDeck clientDeck;
    private List<Position> availablePositions;

    public ClientField() {
        cards = new ArrayList<>();
        side = new HashMap<>();
        field = new HashMap<>();
        clientDeck = new ClientDeck();
        availablePositions = new ArrayList<>();
    }

    public List<Position> getAvailablePositions() {
        return availablePositions;
    }

    public void setAvailablePositions(List<Position> availablePositions) {
        this.availablePositions = availablePositions;
    }

    public void playCard(int id, boolean front, Position position) {
        cards.add(id);
        side.put(id, front);
        field.put(id, position);
    }

    public void printUsedField() {
        int maxX = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getX).max().orElse(40);
        int maxY = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getY).max().orElse(40);
        int minX = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getX).min().orElse(-40);
        int minY = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getY).min().orElse(-40);

        maxX = realX(maxX);
        maxY = realY(maxY);
        minX = realX(minX);
        minY = realY(minY);

        String[] fieldToPrint = generateField();
        for (int i = maxY; i < minY + 7; i++) {
            System.out.println(fieldToPrint[i].substring(minX, maxX + 25));
        }
    }

    private String[] generateField() {
        String[] printableField = new String[327];
        Arrays.fill(printableField, new String(new char[1625]).replace('\u0000', ' '));

        for (int i = 0; i < availablePositions.size(); i++) {
            String[] card = clientDeck.generateAvailablePosition(i);
            for (int j = 0; j < card.length; j++) {
                Position position = availablePositions.get(i);
                String riga = printableField[realY(position.getY()) + j];
                printableField[realY(position.getY()) + j] = riga.substring(0, realX(position.getX())) + card[j] + riga.substring(realX(position.getX()) + 25, 1625);
            }
        }

        for (Integer i : cards) {
            String[] card = clientDeck.generateCardById(i, side.get(i));
            for (int j = 0; j < card.length; j++) {
                String riga = printableField[realY(field.get(i).getY()) + j];
                printableField[realY(field.get(i).getY()) + j] = riga.substring(0, realX(field.get(i).getX())) + card[j] + riga.substring(realX(field.get(i).getX()) + 25, 1625);
            }
        }

        return printableField;
    }

    private int realX(int x) {
        return (40 * 20) + x * 20;
    }

    private int realY(int y) {
        return (8 * 20) - y * 4;
    }
}