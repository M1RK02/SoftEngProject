package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.cards.CardResources;
import it.polimi.ingsw.gc01.model.player.PlayerResources;

public enum Item implements CardResources, PlayerResources {
    QUILL,
    INKWELL,
    MANUSCRIPT;
}