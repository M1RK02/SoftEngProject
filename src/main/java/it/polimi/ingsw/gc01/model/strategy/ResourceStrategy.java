package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.player.Player;

public class ResourceStrategy implements Strategy {
    private final Resource resource;

    public ResourceStrategy(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    public int check(Player player) {
        return (player.getResources().get(resource) / 3) * 2;
    }
}