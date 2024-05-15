package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.network.message.Message;

public interface VirtualView {
    /**
     *
     * @param message
     */
    public void updateRoomId(Message message);

    /**
     *
     * @param message
     */
    public void showAvailableColors(Message message);

    /**
     *
     * @param message
     */
    public void updateReady(Message message);

    /**
     *
     * @param message
     */
    public void showCommonObjective(Message message);

    /**
     *
     * @param message
     */
    public void showTable(Message message);

    /**
     *
     * @param message
     */
    public void showHand(Message message);

    /**
     *
     * @param message
     */
    public void showField(Message message);

    /**
     *
     * @param message
     */
    public void showSecretObjectives(Message message);

    /**
     *
     * @param message
     */
    public void showError(Message message);

    /**
     *
     * @param message
     */
    public void serviceMessage(Message message);
}
