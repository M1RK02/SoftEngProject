package it.polimi.ingsw.gc01.view.tui;

import com.google.gson.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.strategy.Strategy;
import it.polimi.ingsw.gc01.utils.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Class to manage the generation of cards client side
 */
public class ClientDeck {
    /**
     * Map of every possible card
     */
    private final Map<Integer, Card> deck;

    /**
     * Construct a new ClientDeck object reading the json files
     */
    public ClientDeck() {
        deck = new HashMap<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .registerTypeAdapter(ScoreCondition.class, new ScoreConditionDeserializer())
                .registerTypeHierarchyAdapter(Strategy.class, new StrategyAdapter())
                .create();
        String[] types = {"Resource", "Golden", "Starter", "Objective"};
        for (String type : types) {
            InputStream json = this.getClass().getResourceAsStream("/it/polimi/ingsw/gc01/model/decks/" + type + "Deck.json");
            try {
                List<Object> cardList = gson.fromJson(new InputStreamReader(json), List.class);
                for (Object card : cardList) {
                    Card c = gson.fromJson(card.toString(), (Type) Class.forName("it.polimi.ingsw.gc01.model.cards." + type + "Card"));
                    deck.put(c.getId(), c);
                }
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Generate the selected card by id
     *
     * @param id of the card to generate, null for an empty space
     * @param front true for the front of the card, false otherwise
     * @return the printable card as an array of lines
     */
    public String[] generateCardById(Integer id, boolean front) {
        if (id == null) {
            return generateEmptySpace();
        }
        String type = getTypeById(id);
        return switch (type) {
            case "Resource" -> generateResourceCardById(id, front);
            case "Golden" -> generateGoldenCardById(id, front);
            case "Starter" -> generateStarterCardById(id, front);
            default -> generateObjectiveCardById(id);
        };
    }

    /**
     * Get the corresponding card type for the id
     *
     * @param id of the card to generate
     * @return the type of the card to generate
     */
    private String getTypeById(int id) {
        if (id >= 1 && id <= 40) {
            return "Resource";
        }
        if (id >= 41 && id <= 80) {
            return "Golden";
        }
        if (id >= 81 && id <= 86) {
            return "Starter";
        }
        return "Objective";
    }

    /**
     * Generate the selected resource card by id
     *
     * @param id of the card to generate, null for an empty space
     * @param front true for the front of the card, false otherwise
     * @return the printable card as an array of lines
     */
    private String[] generateResourceCardById(int id, boolean front) {
        if (front) {
            return generateResourceCardFrontById(id);
        } else {
            return generateResourceCardBackById(id);
        }
    }

    /**
     * Generate the selected resource card front by id
     *
     * @param id of the card to generate, null for an empty space
     * @return the printable card as an array of lines
     */
    private String[] generateResourceCardFrontById(int id) {
        ResourceCard resourceCard = (ResourceCard) deck.get(id);

        String[] card = generateEmptyCard();

        String topLeft = resourceCard.getCorners().get(CornerPosition.TOP_LEFT).getResource().toString();
        String bottomLeft = resourceCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getResource().toString();
        String topRight = resourceCard.getCorners().get(CornerPosition.TOP_RIGHT).getResource().toString();
        String bottomRight = resourceCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getResource().toString();

        switch (topLeft) {
            case "EMPTY":
                break;
            case "FULL":
                card[0] = card[0].substring(0, 4) + "═" + card[0].substring(5);
                card[1] = card[1].substring(0, 4) + " " + card[1].substring(5);
                card[2] = "║    " + card[2].substring(5);
                break;
            case "INKWELL":
                card[1] = card[1].substring(0, 2) + "K" + card[1].substring(3);
                break;
            default:
                card[1] = card[1].substring(0, 2) + topLeft.charAt(0) + card[1].substring(3);
        }

        switch (bottomLeft) {
            case "EMPTY":
                break;
            case "FULL":
                card[4] = "║    " + card[4].substring(5);
                card[5] = card[5].substring(0, 4) + " " + card[5].substring(5);
                card[6] = card[6].substring(0, 4) + "═" + card[6].substring(5);
                break;
            case "INKWELL":
                card[5] = card[5].substring(0, 2) + "K" + card[5].substring(3);
                break;
            default:
                card[5] = card[5].substring(0, 2) + bottomLeft.charAt(0) + card[5].substring(3);
        }

        switch (topRight) {
            case "EMPTY":
                break;
            case "FULL":
                card[0] = card[0].substring(0, 20) + "═" + card[0].substring(21);
                card[1] = card[1].substring(0, 20) + " " + card[1].substring(21);
                card[2] = card[2].substring(0, 20) + "    ║";
                break;
            case "INKWELL":
                card[1] = card[1].substring(0, 22) + "K" + card[1].substring(23);
                break;
            default:
                card[1] = card[1].substring(0, 22) + topRight.charAt(0) + card[1].substring(23);
        }

        switch (bottomRight) {
            case "EMPTY":
                break;
            case "FULL":
                card[4] = card[4].substring(0, 20) + "    ║";
                card[5] = card[5].substring(0, 20) + " " + card[5].substring(21);
                card[6] = card[6].substring(0, 20) + "═" + card[6].substring(21);
                break;
            case "INKWELL":
                card[5] = card[5].substring(0, 22) + "K" + card[5].substring(23);
                break;
            default:
                card[5] = card[5].substring(0, 22) + bottomRight.charAt(0) + card[5].substring(23);
        }

        card[3] = card[3].substring(0, 11) + resourceCard.getColor().toString().substring(0, 3) + card[3].substring(14);

        int score = resourceCard.getScore();
        if (resourceCard.getScore() != 0) {
            card[0] = card[0].substring(0, 9) + "╦═════╦" + card[0].substring(16);
            card[1] = card[1].substring(0, 9) + "║  " + score + "  ║" + card[1].substring(16);
            card[2] = card[2].substring(0, 9) + "╚═════╝" + card[2].substring(16);
        }

        return card;
    }

    /**
     * Generate the selected resource card back by id
     *
     * @param id of the card to generate, null for an empty space
     * @return the printable card as an array of lines
     */
    private String[] generateResourceCardBackById(int id) {
        ResourceCard resourceCard = (ResourceCard) deck.get(id);

        String[] card = generateEmptyCard();

        CardColor color = resourceCard.getColor();

        card[2] = card[2].substring(0, 10) + "╔═══╗" + card[2].substring(15);
        card[3] = card[3].substring(0, 10) + "║ " + getSymbolByColor(color) + " ║" + card[3].substring(15);
        card[4] = card[4].substring(0, 10) + "╚═══╝" + card[4].substring(15);

        card[1] = card[1].substring(0, 11) + color.toString().substring(0, 3) + card[1].substring(14);

        return card;
    }

    /**
     * Generate the selected golden card by id
     *
     * @param id of the card to generate, null for an empty space
     * @param front true for the front of the card, false otherwise
     * @return the printable card as an array of lines
     */
    private String[] generateGoldenCardById(int id, boolean front) {
        if (front) {
            return generateGoldenCardFrontById(id);
        } else {
            return generateGoldenCardBackById(id);
        }
    }

    /**
     * Generate the selected golden card front by id
     *
     * @param id of the card to generate, null for an empty space
     * @return the printable card as an array of lines
     */
    private String[] generateGoldenCardFrontById(int id) {
        GoldenCard goldenCard = (GoldenCard) deck.get(id);

        String[] card = generateResourceCardFrontById(id);

        ScoreCondition scoreCondition = goldenCard.getScoreCondition();
        int score = goldenCard.getScore();

        if (!scoreCondition.equals(ConditionType.EMPTY)) {
            if (scoreCondition.equals(Item.INKWELL)) {
                card[1] = card[1].substring(0, 11) + score + " " + "K" + card[1].substring(14);
            } else {
                card[1] = card[1].substring(0, 11) + score + " " + scoreCondition.toString().charAt(0) + card[1].substring(14);
            }
        }

        String requirements = "";
        for (Resource res : goldenCard.getRequirements().keySet()) {
            requirements += String.valueOf(res.toString().charAt(0)).repeat(goldenCard.getRequirements().get(res));
        }

        if (!requirements.isEmpty()) {
            card[4] = card[4].substring(0, 9) + "╔═════╗" + card[4].substring(16);
            card[5] = card[5].substring(0, 9) + "║     ║" + card[5].substring(16);
            card[6] = card[6].substring(0, 9) + "╩═════╩" + card[6].substring(16);

            switch (requirements.length()) {
                case 3 -> card[5] = card[5].substring(0, 11) + requirements + card[5].substring(14);
                case 4 ->
                        card[5] = card[5].substring(0, 10) + requirements.substring(0, 2) + " " + requirements.substring(2) + card[5].substring(15);
                case 5 -> card[5] = card[5].substring(0, 10) + requirements + card[5].substring(15);
            }
        }

        return card;
    }

    /**
     * Generate the selected golden card back by id
     *
     * @param id of the card to generate, null for an empty space
     * @return the printable card as an array of lines
     */
    private String[] generateGoldenCardBackById(int id) {
        return generateResourceCardBackById(id);
    }

    /**
     * Generate the selected starter card by id
     *
     * @param id of the card to generate, null for an empty space
     * @param front true for the front of the card, false otherwise
     * @return the printable card as an array of lines
     */
    private String[] generateStarterCardById(int id, boolean front) {
        if (front) {
            return generateStarterCardFrontById(id);
        } else {
            return generateStarterCardBackById(id);
        }
    }

    /**
     * Generate the selected starter card front by id
     *
     * @param id of the card to generate, null for an empty space
     * @return the printable card as an array of lines
     */
    private String[] generateStarterCardFrontById(int id) {
        StarterCard starterCard = (StarterCard) deck.get(id);

        String[] card = generateEmptyCard();

        String topLeft = starterCard.getCorners().get(CornerPosition.TOP_LEFT).getResource().toString();
        String bottomLeft = starterCard.getCorners().get(CornerPosition.BOTTOM_LEFT).getResource().toString();
        String topRight = starterCard.getCorners().get(CornerPosition.TOP_RIGHT).getResource().toString();
        String bottomRight = starterCard.getCorners().get(CornerPosition.BOTTOM_RIGHT).getResource().toString();

        switch (topLeft) {
            case "EMPTY":
                break;
            case "FULL":
                card[0] = card[0].substring(0, 4) + "═" + card[0].substring(5);
                card[1] = card[1].substring(0, 4) + " " + card[1].substring(5);
                card[2] = "║    " + card[2].substring(5);
                break;
            case "INKWELL":
                card[1] = card[1].substring(0, 2) + "K" + card[1].substring(3);
                break;
            default:
                card[1] = card[1].substring(0, 2) + topLeft.charAt(0) + card[1].substring(3);
        }

        switch (bottomLeft) {
            case "EMPTY":
                break;
            case "FULL":
                card[4] = "║    " + card[4].substring(5);
                card[5] = card[5].substring(0, 4) + " " + card[5].substring(5);
                card[6] = card[6].substring(0, 4) + "═" + card[6].substring(5);
                break;
            case "INKWELL":
                card[5] = card[5].substring(0, 2) + "K" + card[5].substring(3);
                break;
            default:
                card[5] = card[5].substring(0, 2) + bottomLeft.charAt(0) + card[5].substring(3);
        }

        switch (topRight) {
            case "EMPTY":
                break;
            case "FULL":
                card[0] = card[0].substring(0, 20) + "═" + card[0].substring(21);
                card[1] = card[1].substring(0, 20) + " " + card[1].substring(21);
                card[2] = card[2].substring(0, 20) + "    ║";
                break;
            case "INKWELL":
                card[1] = card[1].substring(0, 22) + "K" + card[1].substring(23);
                break;
            default:
                card[1] = card[1].substring(0, 22) + topRight.charAt(0) + card[1].substring(23);
        }

        switch (bottomRight) {
            case "EMPTY":
                break;
            case "FULL":
                card[4] = card[4].substring(0, 20) + "    ║";
                card[5] = card[5].substring(0, 20) + " " + card[5].substring(21);
                card[6] = card[6].substring(0, 20) + "═" + card[6].substring(21);
                break;
            case "INKWELL":
                card[5] = card[5].substring(0, 22) + "K" + card[5].substring(23);
                break;
            default:
                card[5] = card[5].substring(0, 22) + bottomRight.charAt(0) + card[5].substring(23);
        }

        card[2] = card[2].substring(0, 10) + "╔═══╗" + card[2].substring(15);
        card[3] = card[3].substring(0, 10) + "║   ║" + card[3].substring(15);
        card[4] = card[4].substring(0, 10) + "╚═══╝" + card[4].substring(15);

        switch (starterCard.getCenterResources().size()) {
            case 1 ->
                    card[3] = card[3].substring(0, 12) + starterCard.getCenterResources().toArray()[0].toString().charAt(0) + card[3].substring(13);
            case 2 ->
                    card[3] = card[3].substring(0, 11) + starterCard.getCenterResources().toArray()[0].toString().charAt(0) + " " + starterCard.getCenterResources().toArray()[1].toString().charAt(0) + card[3].substring(14);
            case 3 ->
                    card[3] = card[3].substring(0, 11) + starterCard.getCenterResources().toArray()[0].toString().charAt(0) + starterCard.getCenterResources().toArray()[1].toString().charAt(0) + starterCard.getCenterResources().toArray()[2].toString().charAt(0) + card[3].substring(14);
        }

        card[1] = card[1].substring(0, 11) + "STR" + card[1].substring(14);

        return card;
    }

    /**
     * Generate the selected starter card back by id
     *
     * @param id of the card to generate, null for an empty space
     * @return the printable card as an array of lines
     */
    private String[] generateStarterCardBackById(int id) {
        StarterCard starterCard = (StarterCard) deck.get(id);

        String[] card = generateEmptyCard();

        String topLeft = starterCard.getBackCorners().get(CornerPosition.TOP_LEFT).getResource().toString();
        String bottomLeft = starterCard.getBackCorners().get(CornerPosition.BOTTOM_LEFT).getResource().toString();
        String topRight = starterCard.getBackCorners().get(CornerPosition.TOP_RIGHT).getResource().toString();
        String bottomRight = starterCard.getBackCorners().get(CornerPosition.BOTTOM_RIGHT).getResource().toString();

        if (topLeft.equals("INKWELL")) {
            card[1] = card[1].substring(0, 2) + "K" + card[1].substring(3);
        } else {
            card[1] = card[1].substring(0, 2) + topLeft.charAt(0) + card[1].substring(3);
        }

        if (bottomLeft.equals("INKWELL")) {
            card[5] = card[5].substring(0, 2) + "K" + card[5].substring(3);
        } else {
            card[5] = card[5].substring(0, 2) + bottomLeft.charAt(0) + card[5].substring(3);
        }

        if (topRight.equals("INKWELL")) {
            card[1] = card[1].substring(0, 22) + "K" + card[1].substring(23);
        } else {
            card[1] = card[1].substring(0, 22) + topRight.charAt(0) + card[1].substring(23);
        }

        if (bottomRight.equals("INKWELL")) {
            card[5] = card[5].substring(0, 22) + "K" + card[5].substring(23);
        } else {
            card[5] = card[5].substring(0, 22) + bottomRight.charAt(0) + card[5].substring(23);
        }


        card[3] = card[3].substring(0, 11) + "STR" + card[3].substring(14);

        return card;
    }

    /**
     * Generate the selected objective card by id
     *
     * @param id of the card to generate, null for an empty space
     * @return the printable card as an array of lines
     */
    private String[] generateObjectiveCardById(int id) {
        String[] objectiveCard = new String[8];

        if (id == 87) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_RED + "╔═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_RED + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_RED + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_RED + "╚═══╝" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 88) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_GREEN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_GREEN + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_GREEN + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_GREEN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 89) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_CYAN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_CYAN + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_CYAN + "╔═══╬═══╝" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_CYAN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 90) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_PURPLE + "╔═══╗" + DefaultValue.ANSI_YELLOW + "              ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║      " + DefaultValue.ANSI_PURPLE + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "          ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║          " + DefaultValue.ANSI_PURPLE + "╚═══╬═══╗" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║              " + DefaultValue.ANSI_PURPLE + "╚═══╝" + DefaultValue.ANSI_YELLOW + "      ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 91) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_RED + "╔═══╗" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_RED + "╠═══╣" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_RED + "╚═══╬" + DefaultValue.ANSI_GREEN + "═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_GREEN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 92) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_GREEN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_GREEN + "╠═══╣" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_PURPLE + "╔═══" + DefaultValue.ANSI_GREEN + "╬═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_PURPLE + "╚═══╝" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 93) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_RED + "╔═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╔═══╬" + DefaultValue.ANSI_RED + "═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╠═══╣" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╚═══╝" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 94) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╔═══╗" + DefaultValue.ANSI_YELLOW + "            ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║        " + DefaultValue.ANSI_CYAN + "╚═══" + DefaultValue.ANSI_PURPLE + "╬═══╗" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_PURPLE + "╠═══╣" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║            " + DefaultValue.ANSI_PURPLE + "╚═══╝" + DefaultValue.ANSI_YELLOW + "        ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 95) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "║    F    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "║  F   F  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_RED + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 96) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "║    P    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "║  P   P  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_GREEN + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 97) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "║    A    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "║  A   A  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_CYAN + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 98) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "╔═════════╗" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "║    I    ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "║  I   I  ║" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║       " + DefaultValue.ANSI_PURPLE + "╚═════════╝" + DefaultValue.ANSI_YELLOW + "       ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 99) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  3  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║     ╔═══╩═════╩═══╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ║    QUILL    ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║   INKWELL   ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║  MANUSCRIPT ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 100) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ╔═════════════╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║  MANUSCRIPT ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║  MANUSCRIPT ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 101) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ╔═════════════╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║   INKWELL   ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║   INKWELL   ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        if (id == 102) {
            objectiveCard[0] = DefaultValue.ANSI_YELLOW + "╔═════════╦═════╦═════════╗" + DefaultValue.ANSI_RESET;
            objectiveCard[1] = DefaultValue.ANSI_YELLOW + "║         ║  2  ║         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[2] = DefaultValue.ANSI_YELLOW + "║         ╚═════╝         ║" + DefaultValue.ANSI_RESET;
            objectiveCard[3] = DefaultValue.ANSI_YELLOW + "║     ╔═════════════╗     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[4] = DefaultValue.ANSI_YELLOW + "║     ║    QUILL    ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[5] = DefaultValue.ANSI_YELLOW + "║     ║    QUILL    ║     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[6] = DefaultValue.ANSI_YELLOW + "║     ╚═════════════╝     ║" + DefaultValue.ANSI_RESET;
            objectiveCard[7] = DefaultValue.ANSI_YELLOW + "╚═════════════════════════╝" + DefaultValue.ANSI_RESET;
        }

        return objectiveCard;
    }

    /**
     * Generate an available position for the field
     *
     * @param index of the position
     * @return the printable position as an array of lines
     */
    public String[] generateAvailablePosition(int index) {
        String[] position = new String[7];
        position[0] = "┌─ ── ── ── ─ ── ── ── ─┐";
        position[1] = "                         ";
        position[2] = "│                       │";
        position[3] = String.format("           %2d            ", index);
        position[4] = "│                       │";
        position[5] = "                         ";
        position[6] = "└─ ── ── ── ─ ── ── ── ─┘";
        return position;
    }

    /**
     * Generate an empty space shaped like a card
     *
     * @return the printable space as an array of lines
     */
    private String[] generateEmptySpace() {
        String[] space = new String[7];
        space[0] = "┌───────────────────────┐";
        space[1] = "│                       │";
        space[2] = "│                       │";
        space[3] = "│                       │";
        space[4] = "│                       │";
        space[5] = "│                       │";
        space[6] = "└───────────────────────┘";
        return space;
    }

    /**
     * Generate an empty card (base for the resource and golden cards)
     *
     * @return the printable card as an array of lines
     */
    private String[] generateEmptyCard() {
        String[] card = new String[7];
        card[0] = "╔═══╦═══════════════╦═══╗";
        card[1] = "║   ║               ║   ║";
        card[2] = "╠═══╝               ╚═══╣";
        card[3] = "║                       ║";
        card[4] = "╠═══╗               ╔═══╣";
        card[5] = "║   ║               ║   ║";
        card[6] = "╚═══╩═══════════════╩═══╝";
        return card;
    }

    /**
     * Get the correct symbol associated with the color
     * @param color of the card
     * @return the first letter of the corresponding symbol
     */
    private char getSymbolByColor(CardColor color) {
        return switch (color) {
            case RED -> 'F';
            case GREEN -> 'P';
            case BLUE -> 'A';
            default -> 'I';
        };
    }
}