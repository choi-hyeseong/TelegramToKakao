package com.division.telegramtokakao.data;

public class TelegramSendData {

    private long chatID; //채팅방 아이디
    private int channel; //송수신에 매칭될 채널
    private String botToken; //봇 토큰
    private int apiHash;

    public TelegramSendData(long chatID, int channel, String botToken, int apiHash) {
        this.chatID = chatID;
        this.channel = channel;
        this.botToken = botToken;
        this.apiHash = apiHash;
    }

    public long getChatID() {
        return chatID;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setChatID(long chatID) {
        this.chatID = chatID;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getApiHash() {
        return apiHash;
    }

    public void setApiHash(int apiHash) {
        this.apiHash = apiHash;
    }
}
