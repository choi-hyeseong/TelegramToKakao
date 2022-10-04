package com.division.telegramtokakao.data;

public class PeriodMessageData{

    private String roomName;
    private String message;
    private long lastSend;
    private int period;
    private final int INIT_PERIOD;

    public PeriodMessageData(String message, String roomName, int period) {
        this.message = message;
        this.roomName = roomName;
        this.lastSend = 0;
        this.period = period;
        INIT_PERIOD = period;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getLastSend() {
        return lastSend;
    }

    public void setLastSend(long lastSend) {
        this.lastSend = lastSend;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void resetPeriod() {
        period = INIT_PERIOD;
    }
}
