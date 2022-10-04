package com.division.telegramtokakao.data;

public class KakaoTalkData implements ReceiveData{

    private String chatName;
    private int channel;
    private String filter;

    public KakaoTalkData(String chatName, int channel) {
        this.channel= channel;
        this.chatName = chatName;
        this.filter = null;
    }

    public KakaoTalkData(String chatName, int channel, String filter) {
        this.channel= channel;
        this.chatName = chatName;
        this.filter = filter;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public void setFilter(String filter) {
        this.filter = filter;
    }
}
