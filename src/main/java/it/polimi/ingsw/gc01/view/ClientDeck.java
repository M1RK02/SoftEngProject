package it.polimi.ingsw.gc01.view;

import com.google.gson.*;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.decks.*;
import it.polimi.ingsw.gc01.model.strategy.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class ClientDeck {
    private final Map<Integer, Card> deck;

    public ClientDeck(){
        deck = new HashMap<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .registerTypeAdapter(ScoreCondition.class, new ScoreConditionDeserializer())
                .registerTypeHierarchyAdapter(Strategy.class, new StrategyAdapter())
                .create();
        String[] types = {"Resource", "Golden", "Starter", "Objective"};
        for (String type : types) {
            String json = "src/main/resources/it/polimi/ingsw/gc01/model/decks/"+type+"Deck.json";
            try{
                List<Object> cardList = gson.fromJson(new FileReader(json), List.class);
                for (Object card : cardList) {
                    Card c = gson.fromJson(card.toString(), (Type) Class.forName("it.polimi.ingsw.gc01.model.cards."+type+"Card"));
                    deck.put(c.getId(), c);
                }
            } catch (Exception ignored){}
        }
    }

    public String[] generateCardById(int id) {
        if (id >= 1 && id <= 40) {
            return generateResourceCardById(id);
        }
        if (id >= 41 && id <= 80) {
            return generateGoldenCardById(id);
        }
        if (id >= 81 && id <= 86) {
            return generateStarterCardById(id);
        }
        if (id >= 87 && id <= 102) {
            return generateObjectiveCardById(id);
        }
        return null;
    }

    private String[] generateResourceCardById(int id) {
        ResourceCard card = (ResourceCard) deck.get(id);

        // CARTA VUOTA
        String[] carta = new String[7];
        String color = card.getColor().toString().substring(0, 3);
        carta[0] = "╔═══╦═══════════════╦═══╗";
        carta[1] = "║ x ║               ║ w ║";
        carta[2] = "╠═══╝               ╚═══╣";
        carta[3] = "║          " + color + "          ║";
        carta[4] = "╠═══╗               ╔═══╣";
        carta[5] = "║ y ║               ║ z ║";
        carta[6] = "╚═══╩═══════════════╩═══╝";

        // SIMBOLI AI CORNER
        char x = ' ', y = ' ', w = ' ', z = ' ';
        String topLeft = card.getCorners().get(CornerPosition.TOP_LEFT).getResource().toString();
        String bottomLeft = card.getCorners().get(CornerPosition.BOTTOM_LEFT).getResource().toString();
        String topRight = card.getCorners().get(CornerPosition.TOP_RIGHT).getResource().toString();
        String bottomRight = card.getCorners().get(CornerPosition.BOTTOM_RIGHT).getResource().toString();

        switch (topLeft) {
            case "EMPTY" -> x = ' ';
            case "FULL" -> {
                carta[0] = carta[0].substring(0, 4) + "═" + carta[0].substring(5);
                carta[1] = carta[1].substring(0, 4) + " " + carta[1].substring(5);
                carta[2] = "║    " + carta[2].substring(5);
            }
            default -> x = topLeft.charAt(0);
        }

        switch (bottomLeft) {
            case "EMPTY" -> y = ' ';
            case "FULL" -> {
                carta[4] = "║    " + carta[4].substring(5);
                carta[5] = carta[5].substring(0, 4) + " " + carta[5].substring(5);
                carta[6] = carta[6].substring(0, 4) + "═" + carta[6].substring(5);
            }
            default -> y = bottomLeft.charAt(0);
        }

        switch (topRight) {
            case "EMPTY" -> w = ' ';
            case "FULL" -> {
                carta[0] = carta[0].substring(0, 20) + "═" + carta[0].substring(21);
                carta[1] = carta[1].substring(0, 20) + " " + carta[1].substring(21);
                carta[2] = carta[2].substring(0, 20) + "    ║";
            }
            default -> w = topRight.charAt(0);
        }

        switch (bottomRight) {
            case "EMPTY" -> z = ' ';
            case "FULL" -> {
                carta[4] = carta[4].substring(0, 20) + "    ║";
                carta[5] = carta[5].substring(0, 20) + " " + carta[5].substring(21);
                carta[6] = carta[6].substring(0, 20) + "═" + carta[6].substring(21);
            }
            default -> z = bottomRight.charAt(0);
        }

        carta[1] = carta[1].replace('x', x);
        carta[5] = carta[5].replace('y', y);
        carta[1] = carta[1].replace('w', w);
        carta[5] = carta[5].replace('z', z);

        return carta;
    }

    private String[] generateGoldenCardById(int id) {
        //TODO
        return null;
    }

    private String[] generateStarterCardById(int id) {
        StarterCard card = (StarterCard) deck.get(id);

        // CARTA VUOTA
        String[] carta = new String[7];
        carta[0] = DefaultValue.ANSI_YELLOW + "╔═══╦═══════════════╦═══╗        ╔═══╦═══════════════╦═══╗" + DefaultValue.ANSI_RESET;
        carta[1] = DefaultValue.ANSI_YELLOW + "║ x ║               ║ w ║        ║ h ║               ║ k ║" + DefaultValue.ANSI_RESET;
        carta[2] = DefaultValue.ANSI_YELLOW + "╠═══╝     ╔═══╗     ╚═══╣        ╠═══╝               ╚═══╣" + DefaultValue.ANSI_RESET;
        carta[3] = DefaultValue.ANSI_YELLOW + "║         ║abc║         ║        ║                       ║" + DefaultValue.ANSI_RESET;
        carta[4] = DefaultValue.ANSI_YELLOW + "╠═══╗     ╚═══╝     ╔═══╣        ╠═══╗               ╔═══╣" + DefaultValue.ANSI_RESET;
        carta[5] = DefaultValue.ANSI_YELLOW + "║ y ║               ║ z ║        ║ j ║               ║ l ║" + DefaultValue.ANSI_RESET;
        carta[6] = DefaultValue.ANSI_YELLOW + "╚═══╩═══════════════╩═══╝        ╚═══╩═══════════════╩═══╝" + DefaultValue.ANSI_RESET;

        // SIMBOLI CENTRALI
        char a = ' ', b = ' ', c = ' ', x = ' ', y = ' ', w = ' ', z = ' ', h = ' ', j = ' ', k = ' ', l = ' ';
        switch (card.getCenterResources().size()) {
            case 1 -> {
                b = card.getCenterResources().toArray()[0].toString().charAt(0);
            }
            case 2 -> {
                a = card.getCenterResources().toArray()[0].toString().charAt(0);
                c = card.getCenterResources().toArray()[1].toString().charAt(0);
            }
            case 3 -> {
                a = card.getCenterResources().toArray()[0].toString().charAt(0);
                b = card.getCenterResources().toArray()[1].toString().charAt(0);
                c = card.getCenterResources().toArray()[2].toString().charAt(0);
            }
        }

        // SIMBOLI AI CORNER
        String topLeft = card.getCorners().get(CornerPosition.TOP_LEFT).getResource().toString();
        String bottomLeft = card.getCorners().get(CornerPosition.BOTTOM_LEFT).getResource().toString();
        String topRight = card.getCorners().get(CornerPosition.TOP_RIGHT).getResource().toString();
        String bottomRight = card.getCorners().get(CornerPosition.BOTTOM_RIGHT).getResource().toString();

        h = card.getBackCorners().get(CornerPosition.TOP_LEFT).getResource().toString().charAt(0);
        j = card.getBackCorners().get(CornerPosition.BOTTOM_LEFT).getResource().toString().charAt(0);
        k = card.getBackCorners().get(CornerPosition.TOP_RIGHT).getResource().toString().charAt(0);
        l = card.getBackCorners().get(CornerPosition.BOTTOM_RIGHT).getResource().toString().charAt(0);

        switch (topLeft) {
            case "EMPTY" -> x = ' ';
            case "FULL" -> {
                carta[0] = carta[0].substring(0, 9) + "═" + carta[0].substring(10);
                carta[1] = carta[1].substring(0, 9) + " " + carta[1].substring(10);
                carta[2] = carta[2].substring(0, 5) + "║    " + carta[2].substring(10);
            }
            default -> x = topLeft.charAt(0);
        }

        switch (bottomLeft) {
            case "EMPTY" -> y = ' ';
            case "FULL" -> {
                carta[4] = carta[4].substring(0, 5) + "║    " + carta[4].substring(10);
                carta[5] = carta[5].substring(0, 9) + " " + carta[5].substring(10);
                carta[6] = carta[6].substring(0, 9) + "═" + carta[6].substring(10);
            }
            default -> y = bottomLeft.charAt(0);
        }

        switch (topRight) {
            case "EMPTY" -> w = ' ';
            case "FULL" -> {
                carta[0] = carta[0].substring(0, 25) + "═" + carta[0].substring(26);
                carta[1] = carta[1].substring(0, 25) + " " + carta[1].substring(26);
                carta[2] = carta[2].substring(0, 25) + "    ║" + carta[2].substring(30);
            }
            default -> w = topRight.charAt(0);
        }

        switch (bottomRight) {
            case "EMPTY" -> z = ' ';
            case "FULL" -> {
                carta[4] = carta[4].substring(0, 25) + "    ║" + carta[4].substring(30);
                carta[5] = carta[5].substring(0, 25) + " " + carta[5].substring(26);
                carta[6] = carta[6].substring(0, 25) + "═" + carta[6].substring(26);
            }
            default -> z = bottomRight.charAt(0);
        }

        carta[3] = carta[3].replace('a', a);
        carta[3] = carta[3].replace('b', b);
        carta[3] = carta[3].replace('c', c);
        carta[1] = carta[1].replace('x', x);
        carta[5] = carta[5].replace('y', y);
        carta[1] = carta[1].replace('w', w);
        carta[5] = carta[5].replace('z', z);
        carta[1] = carta[1].replace('h', h);
        carta[5] = carta[5].replace('j', j);
        carta[1] = carta[1].replace('k', k);
        carta[5] = carta[5].replace('l', l);

        return carta;
    }

    private String[] generateObjectiveCardById(int id) {
        String[] objectiveCard = new String[8];

        if(id == 87) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_RED + "╔═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_RED + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_RED + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_RED + "╚═══╝" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 88) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_GREEN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_GREEN + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_GREEN + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_GREEN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 89) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_CYAN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_CYAN + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_CYAN + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_CYAN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 90) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_PURPLE + "╔═══╗" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_PURPLE + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_PURPLE + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_PURPLE + "╚═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 91) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_RED + "╔═══╗" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_RED + "╠═══╣" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_RED + "╚═══╬" + DefaultValue.ANSI_GREEN + "═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_GREEN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 92) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_GREEN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_GREEN + "╠═══╣" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_PURPLE + "╔═══" + DefaultValue.ANSI_GREEN + "╬═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_PURPLE + "╚═══╝" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 93) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_RED + "╔═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╔═══╬" + DefaultValue.ANSI_RED + "═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╠═══╣" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 94) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╚═══" + DefaultValue.ANSI_PURPLE + "╬═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_PURPLE + "╠═══╣" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_PURPLE + "╚═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 95) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "║    F    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "║  F   F  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 96) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "║    P    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "║  P   P  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 97) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "║    A    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "║  A   A  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 98) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "║    I    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "║  I   I  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 99) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║     ╔═══╩═════╩═══╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ║    QUILL    ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║   INKWELL   ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║  MANUSCRIPT ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 100) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ╔═════════════╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║  MANUSCRIPT ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║  MANUSCRIPT ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 101) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ╔═════════════╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║   INKWELL   ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║   INKWELL   ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if(id == 102) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ╔═════════════╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║    QUILL    ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║    QUILL    ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        return objectiveCard;
    }
}