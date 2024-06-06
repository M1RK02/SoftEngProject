package it.polimi.ingsw.gc01.model.player;

import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.decks.*;
import it.polimi.ingsw.gc01.model.VirtualViewStub;
import it.polimi.ingsw.gc01.network.ObserverManager;
import org.junit.jupiter.api.*;

import java.util.*;

import static it.polimi.ingsw.gc01.model.CornerValue.FULL;
import static it.polimi.ingsw.gc01.model.Resource.*;
import static it.polimi.ingsw.gc01.model.cards.CardColor.*;
import static it.polimi.ingsw.gc01.model.corners.CornerPosition.*;

class PlayerTest {
    static private Player player;
    static private GoldenDeck goldenDeck;
    static private StarterDeck starterDeck;
    static private ResourceDeck resourceDeck;

    @BeforeEach
    void setUp() {
        goldenDeck = new GoldenDeck();
        starterDeck = new StarterDeck();
        resourceDeck = new ResourceDeck();
        ObserverManager notifier = new ObserverManager();
        notifier.addObserver("testPlayer", new VirtualViewStub());
        player = new Player("testPlayer", notifier);
        player.getHand().add(starterDeck.pick());
    }

    @Test
    void playCard() {
        Random generatore = new Random();
        int x, y, z, c, n;
        y = generatore.nextInt(2);

        //Choose random side of starterCard and plays it
        if (y == 1) {
            (player.getHand().get(0)).setFront(true);
        }
        player.playCard(player.getHand().get(0), new Position(0, 0));

        //Draw the first three card
        player.getHand().add(resourceDeck.pick());
        player.getHand().add(resourceDeck.pick());
        player.getHand().add(goldenDeck.pick());

        //Play all other cards
        for (int i = 0; i < 80; i++) {
            x = generatore.nextInt(player.getField().getAvailablePositions().size());
            c = generatore.nextInt(player.getHand().size());
            Position[] availablePositionsArray = new Position[player.getField().getAvailablePositions().size()];

            //Choose random card and position from hand and availablePosition
            ResourceCard card = (ResourceCard) player.getHand().get(c);
            Position position = player.getField().getAvailablePositions().toArray(availablePositionsArray)[x];

            //Memorize score and resources before playCard
            int oldScore = player.getPoints();
            Map<PlayerResource, Integer> oldResources = getOldResources(getAdjacentCards(position));

            //Choose random side of the card end plays it
            z = generatore.nextInt(2);
            if (z == 1) {
                card.setFront(true);
            }
            player.playCard(card, position);

            //Check covered corners
            Map<CornerPosition, PlayableCard> adjacentCards = getAdjacentCards(position);
            for (CornerPosition cornerPosition : adjacentCards.keySet()) {
                Map<CornerPosition, Corner> corners;
                if (adjacentCards.get(cornerPosition) instanceof StarterCard && !adjacentCards.get(cornerPosition).isFront()) {
                    corners = ((StarterCard) adjacentCards.get(cornerPosition)).getBackCorners();
                } else {
                    corners = adjacentCards.get(cornerPosition).getCorners();
                }

                switch (cornerPosition) {
                    case TOP_LEFT:
                        assert (corners.get(BOTTOM_RIGHT).isCovered());
                        break;
                    case TOP_RIGHT:
                        assert (corners.get(BOTTOM_LEFT).isCovered());
                        break;
                    case BOTTOM_LEFT:
                        assert (corners.get(TOP_RIGHT).isCovered());
                        break;
                    case BOTTOM_RIGHT:
                        assert (corners.get(TOP_LEFT).isCovered());
                        break;
                    default:
                }
            }

            //Check resources
            if (!card.isFront()) {
                if (card.getColor().equals(RED)) {
                    assert (player.getResources().get(FUNGI).equals(oldResources.get(FUNGI) + 1));
                } else if (card.getColor().equals(BLUE)) {
                    assert (player.getResources().get(ANIMAL).equals(oldResources.get(ANIMAL) + 1));
                } else if (card.getColor().equals(GREEN)) {
                    assert (player.getResources().get(PLANT).equals(oldResources.get(PLANT) + 1));
                } else assert !card.getColor().equals(PURPLE) || (player.getResources().get(INSECT).equals(oldResources.get(INSECT) + 1));
            } else {
                Map<PlayerResource, Integer> cardResources = cornerResources(card.getCorners());
                for (PlayerResource resource : player.getResources().keySet()) {
                    assert !cardResources.containsKey(resource) || (player.getResources().get(resource).equals(cardResources.get(resource) + oldResources.get(resource)));
                }
            }

            //Check hand
            assert (!player.getHand().contains(card));

            //Check AvailablePositions
            assert (!player.getField().getAvailablePositions().contains(new Position(0, 0)));
            for (CornerPosition cornerPosition : card.getCorners().keySet()) {
                if (!getAdjacentCards(position).containsKey(cornerPosition)) {
                    Position p;
                    switch (cornerPosition) {
                        case TOP_LEFT:
                            p = new Position(position.getX() - 1, position.getY() + 1);
                            if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                                assert (player.getField().getUnavailablePositions().contains(p));
                                assert (!player.getField().getAvailablePositions().contains(p));
                            } else {
                                assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                            }
                            break;
                        case TOP_RIGHT:
                            p = new Position(position.getX() + 1, position.getY() + 1);
                            if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                                assert (player.getField().getUnavailablePositions().contains(p));
                                assert (!player.getField().getAvailablePositions().contains(p));
                            } else {
                                assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                            }
                            break;
                        case BOTTOM_LEFT:
                            p = new Position(position.getX() - 1, position.getY() - 1);
                            if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                                assert (player.getField().getUnavailablePositions().contains(p));
                                assert (!player.getField().getAvailablePositions().contains(p));
                            } else {
                                assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                            }
                            break;
                        case BOTTOM_RIGHT:
                            p = new Position(position.getX() + 1, position.getY() - 1);
                            if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                                assert (player.getField().getUnavailablePositions().contains(p));
                                assert (!player.getField().getAvailablePositions().contains(p));
                            } else {
                                assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                            }
                            break;
                        default:
                    }
                }
            }

            //Check positions
            assert (player.getField().getPositions().containsKey(position));
            assert (player.getField().getPositions().get(position).equals(card));

            //Check score
            if(card.isFront()){
                if (card instanceof GoldenCard) {
                    assert (player.getPoints() == oldScore + ((GoldenCard) card).calculatePoints(player, position));
                } else {
                    assert (player.getPoints() == oldScore + card.getScore());
                }
            }

            n = generatore.nextInt(2);
            if ((n == 1 || goldenDeck.isEmpty()) && !resourceDeck.isEmpty()) {
                player.getHand().add(resourceDeck.pick());
            }
            if ((n == 0 || resourceDeck.isEmpty()) && !goldenDeck.isEmpty()) {
                player.getHand().add(goldenDeck.pick());
            }
        }
    }

    @Test
    void testPlayStarterCardFront() {
        StarterCard card = (StarterCard) player.getHand().get(0);
        card.setFront(true);
        player.playCard(card, new Position(0, 0));

        //Check resources
        Map<PlayerResource, Integer> cardResources = cornerResources(card.getCorners());
        for (Resource resource : card.getCenterResources()) {
            if (cardResources.containsKey(resource)) {
                cardResources.put(resource, cardResources.get(resource) + 1);
            } else {
                cardResources.put(resource, 1);
            }
        }
        for (PlayerResource resource : player.getResources().keySet()) {
            assert !cardResources.containsKey(resource) || (player.getResources().get(resource).equals(cardResources.get(resource)));
        }

        //Check hand
        assert (!player.getHand().contains(card));

        //Check AvailablePositions
        assert (!player.getField().getAvailablePositions().contains(new Position(0, 0)));
        for (CornerPosition cornerPosition : card.getCorners().keySet()) {
            Position p;
            switch (cornerPosition) {
                case TOP_LEFT:
                    p = new Position(-1, +1);
                    if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                case TOP_RIGHT:
                    p = new Position(+1, +1);
                    if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                case BOTTOM_LEFT:
                    p = new Position(-1, -1);
                    if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                case BOTTOM_RIGHT:
                    p = new Position(+1, -1);
                    if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                default:
            }
        }

        //Check Positions
        assert (player.getField().getPositions().containsKey(new Position(0, 0)));
        assert (player.getField().getPositions().get(new Position(0, 0)).equals(card));
    }

    @Test
    void testPlayStarterCardBack() {
        StarterCard card = (StarterCard) player.getHand().get(0);
        card.setFront(false);
        player.playCard(card, new Position(0, 0));

        //Check resources
        Map<PlayerResource, Integer> cardResources = cornerResources(card.getBackCorners());
        for (PlayerResource resource : player.getResources().keySet()) {
            assert !cardResources.containsKey(resource) || (player.getResources().get(resource).equals(cardResources.get(resource)));
        }

        //Check hand
        assert (!player.getHand().contains(card));

        //Check AvailablePositions
        assert (!player.getField().getAvailablePositions().contains(new Position(0, 0)));
        for (CornerPosition cornerPosition : card.getBackCorners().keySet()) {
            Position p;
            switch (cornerPosition) {
                case TOP_LEFT:
                    p = new Position(-1, +1);
                    if (card.getBackCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                case TOP_RIGHT:
                    p = new Position(+1, +1);
                    if (card.getBackCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                case BOTTOM_LEFT:
                    p = new Position(-1, -1);
                    if (card.getBackCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                case BOTTOM_RIGHT:
                    p = new Position(+1, -1);
                    if (card.getBackCorners().get(cornerPosition).getResource().equals(FULL)) {
                        assert (player.getField().getUnavailablePositions().contains(p));
                    } else {
                        assert player.getField().getUnavailablePositions().contains(p) || (player.getField().getAvailablePositions().contains(p));
                    }
                    break;
                default:
            }
        }

        //Check Positions
        assert (player.getField().getPositions().containsKey(new Position(0, 0)));
        assert (player.getField().getPositions().get(new Position(0, 0)).equals(card));
    }

    private Map<PlayerResource, Integer> cornerResources(Map<CornerPosition, Corner> corners) {
        Map<PlayerResource, Integer> cardResources = new HashMap<>();
        for (Corner corner : corners.values()) {
            if (corner.getResource() instanceof PlayerResource) {
                if (cardResources.containsKey((PlayerResource) corner.getResource())) {
                    cardResources.put((PlayerResource) corner.getResource(), cardResources.get((PlayerResource) corner.getResource()) + 1);
                } else {
                    cardResources.put((PlayerResource) corner.getResource(), 1);
                }
            }
        }
        return cardResources;
    }

    private Map<CornerPosition, PlayableCard> getAdjacentCards(Position position) {
        Map<CornerPosition, PlayableCard> adjacentCards = new HashMap<>();
        Position p = new Position(position.getX() - 1, position.getY() - 1);
        if (player.getField().getPositions().containsKey(p)) {
            adjacentCards.put(CornerPosition.BOTTOM_LEFT, player.getField().getPositions().get(p));
        }
        p = new Position(position.getX() - 1, position.getY() + 1);
        if (player.getField().getPositions().containsKey(p)) {
            adjacentCards.put(CornerPosition.TOP_LEFT, player.getField().getPositions().get(p));
        }
        p = new Position(position.getX() + 1, position.getY() + 1);
        if (player.getField().getPositions().containsKey(p)) {
            adjacentCards.put(CornerPosition.TOP_RIGHT, player.getField().getPositions().get(p));
        }
        p = new Position(position.getX() + 1, position.getY() - 1);
        if (player.getField().getPositions().containsKey(p)) {
            adjacentCards.put(CornerPosition.BOTTOM_RIGHT, player.getField().getPositions().get(p));
        }
        return adjacentCards;
    }

    private Map<PlayerResource, Integer> getOldResources(Map<CornerPosition, PlayableCard> adjacentCard) {
        Map<PlayerResource, Integer> oldResources = new HashMap<>();
        for (PlayerResource p : player.getResources().keySet()) {
            oldResources.put(p, player.getResources().get(p));
        }
        for (CornerPosition cornerPosition : adjacentCard.keySet()) {
            Map<CornerPosition, Corner> corners;
            if (adjacentCard.get(cornerPosition) instanceof StarterCard && !adjacentCard.get(cornerPosition).isFront()) {
                corners = ((StarterCard) adjacentCard.get(cornerPosition)).getBackCorners();
            } else {
                corners = adjacentCard.get(cornerPosition).getCorners();
            }

            switch (cornerPosition) {
                case TOP_LEFT:
                    if (corners.get(BOTTOM_RIGHT).getResource() instanceof PlayerResource) {
                        oldResources.put((PlayerResource) corners.get(BOTTOM_RIGHT).getResource(), oldResources.get((PlayerResource) corners.get(BOTTOM_RIGHT).getResource()) - 1);
                    }
                    break;
                case TOP_RIGHT:
                    if (corners.get(BOTTOM_LEFT).getResource() instanceof PlayerResource) {
                        oldResources.put((PlayerResource) corners.get(BOTTOM_LEFT).getResource(), oldResources.get((PlayerResource) corners.get(BOTTOM_LEFT).getResource()) - 1);
                    }
                    break;
                case BOTTOM_LEFT:
                    if (corners.get(TOP_RIGHT).getResource() instanceof PlayerResource) {
                        oldResources.put((PlayerResource) corners.get(TOP_RIGHT).getResource(), oldResources.get((PlayerResource) corners.get(TOP_RIGHT).getResource()) - 1);
                    }
                    break;
                case BOTTOM_RIGHT:
                    if (corners.get(TOP_LEFT).getResource() instanceof PlayerResource) {
                        oldResources.put((PlayerResource) corners.get(TOP_LEFT).getResource(), oldResources.get((PlayerResource) corners.get(TOP_LEFT).getResource()) - 1);
                    }
                    break;
                default:
            }
        }
        return oldResources;
    }
}