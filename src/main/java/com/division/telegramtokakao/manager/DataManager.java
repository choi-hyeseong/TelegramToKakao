package com.division.telegramtokakao.manager;

import com.division.telegramtokakao.data.PeriodMessageData;
import com.division.telegramtokakao.data.ReceiveData;
import com.division.telegramtokakao.data.TelegramSendData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataManager {

    private final List<TelegramSendData> sendData;
    private final List<ReceiveData> receiveData;
    private final List<PeriodMessageData> messageData;


    public DataManager() {
        //동시성 문제
        receiveData = Collections.synchronizedList(new ArrayList<>());
        sendData = Collections.synchronizedList(new ArrayList<>());
        messageData = Collections.synchronizedList(new ArrayList<>());
    }

    public void addSendData(String token, long roomId, int channel, int apiHash) {
        sendData.add(new TelegramSendData(roomId, channel, token, apiHash));
    }

    public List<TelegramSendData> getSendData() {
        return sendData;
    }

    public void addReceiveData(ReceiveData data) {
        receiveData.add(data);
    }

    public List<ReceiveData> getReceiveData() {
        return receiveData;
    }

    public void addPeriodData(String roomName, String message, int period) {
        messageData.add(new PeriodMessageData(message, roomName, period));
    }

    public List<PeriodMessageData> getPeriodData() {
        return messageData;
    }
}
