package com.division.telegramtokakao.data;

public class MessageForm {

    private final String message;
    private final boolean isKakao;
    private final long roomId;
    private final String roomName;

    public MessageForm(String message, boolean isKakao, long roomId, String roomName) {
        this.message = message;
        this.isKakao = isKakao;
        this.roomId = roomId;
        this.roomName = roomName;

    }

    public String getMessage() {
        return message;
    }

    public boolean isKakao() {
        return isKakao;
    }


    public long getRoomId() {
        return roomId;
    }


    public String getRoomName() {
        return roomName;
    }

}
