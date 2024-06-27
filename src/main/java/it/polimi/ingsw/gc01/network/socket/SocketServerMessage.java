package it.polimi.ingsw.gc01.network.socket;

/**
 * Socket Messages from server to Client
 */
public enum SocketServerMessage {
    UPDATE_ROOM_ID,
    SHOW_PLAYERS,
    SHOW_PLAYER_JOINED,
    SHOW_PLAYER_LEFT,
    SHOW_WAITING_FOR,
    START_GAME,
    UPDATE_CURRENT_PLAYER,
    SHOW_STARTER,
    SHOW_AVAILABLE_COLORS,
    UPDATE_READY,
    SHOW_COMMON_OBJECTIVES,
    SHOW_TABLE,
    UPDATE_TABLE,
    SHOW_HAND,
    UPDATE_HAND,
    SHOW_FIELD,
    SHOW_POINTS,
    SHOW_SECRET_OBJECTIVES,
    SHOW_ERROR,
    SHOW_LAST_CIRCLE,
    SHOW_WINNERS,
    UPDATE_FIELD,
    IS_ALIVE,
    BACK_TO_MENU,
    CHAT_MESSAGE
}
