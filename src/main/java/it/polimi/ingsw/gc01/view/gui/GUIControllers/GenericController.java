package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.GUI;

/**
 * Generic controller class
 */
public abstract class GenericController {
    protected GUI gui;

    /**
     * @param gui user interface to set
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    /**
     *
     * @param o attributes to be set
     */
    public void setAttributes(Object... o) {
    }
}