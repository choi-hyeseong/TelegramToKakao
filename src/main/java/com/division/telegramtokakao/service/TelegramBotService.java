package com.division.telegramtokakao.service;

import com.division.telegramtokakao.manager.DataManager;
import com.division.telegramtokakao.manager.PeriodMessageSender;
import com.division.telegramtokakao.manager.TelegramManager;
import it.tdlight.client.SimpleTelegramClient;

import java.util.ArrayList;

public class TelegramBotService {

    private boolean isServiceRunning;
    private final TelegramManager manager;
    private final DataManager dataManager;
    private Thread botThread;
    public static MessageSendQueue sendQueue;
    private final PeriodMessageSender sender;

    public TelegramBotService(TelegramManager telegramManager, DataManager manager) {
        isServiceRunning = false;
        this.manager = telegramManager;
        this.dataManager = manager;
        botThread = null;
        sendQueue = new MessageSendQueue(this);
        this.sender = new PeriodMessageSender(manager, sendQueue);
    }

    public boolean isRunning() {
        return isServiceRunning;
    }

    public void run() {
        if (!isServiceRunning) {
            sendQueue.run();
            isServiceRunning = true;
            botThread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        new ArrayList<>(dataManager.getSendData()).forEach(data -> manager.addBot(data.getBotToken(), data.getApiHash()));
                        Thread.sleep(5000); //5초 단위 슬립
                    }
                }
                catch (InterruptedException e) {
                }

            });
            botThread.start();
            sender.start();
        }
    }

    public void stop() {
        if (isServiceRunning) {
            if (sendQueue != null)
                sendQueue.stop();
            isServiceRunning = false;
            if (botThread != null)
                botThread.interrupt();
            sender.stop();
            manager.getBotList().forEach(client -> client.getClient().sendClose());
            manager.clear();

            //manager.getBotList().forEach(BotSession::stop); 30초 이상 걸리는 메소드로 사용 중지
        }
    }


}
