package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.player.Player;

public class ResourceStrategy implements Strategy {
    private final Resource resource;

    /**
     * Constructor of the ResourceStrategy
     * @param resource the resource corresponding to the ResourceStrategyCard
     */
    public ResourceStrategy(Resource resource) {
        this.resource = resource;
    }

    /**
     *
     * @return the resource of the ResourceStrategy card
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Checks the quantity of time the player's field respected the specified resources to achieve the resource strategy
     *
     * @param player The player whose resources will be checked.
     * @return the number of points gained by the player due to the resource Strategy.
     */
    public int check(Player player) {
        return (player.getResources().get(resource) / 3) * 2;
    }
}