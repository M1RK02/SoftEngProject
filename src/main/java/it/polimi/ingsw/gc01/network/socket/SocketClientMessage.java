package it.polimi.ingsw.gc01.network.socket;

/**
 * Socket Client Messages to the server
 */
public enum SocketClientMessage {
    CREATE_GAME,
    JOIN_GAME,
    JOIN_FIRST_GAME,
    CHOOSE_COLOR,
    SWITCH_READY,
    CHOOSE_SECRET_OBJECTIVE,
    FLIP_CARD,
    PLAY_CARD,
    DRAW_CARD,
    LEAVE,
    CHAT_MESSAGE
}
