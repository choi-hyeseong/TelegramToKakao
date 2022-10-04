package com.division.telegramtokakao.data;

public class TelegramReceiveData implements ReceiveData{

    private long chatID; //채팅방 아이디
    private int channel; //송수신에 매칭될 채널
    private String filter; //그룹방에서 메시지 필터링할 사용자 id (ex 159784,5465465)

    public TelegramReceiveData(long chatID, int channel) {
        this.chatID = chatID;
        this.channel = channel;
        this.filter = null;
    }

    public TelegramReceiveData(long chatID, int channel, String filter) {
        this.chatID = chatID;
        this.channel = channel;
        this.filter = filter;
    }

    public long getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
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
