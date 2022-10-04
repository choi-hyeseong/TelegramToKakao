package com.division.telegramtokakao.service;

import com.division.telegramtokakao.data.MessageForm;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.manager.TelegramManager;
import com.division.telegramtokakao.util.SendMessageUtil;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MessageSendQueue {

    private final Queue<MessageForm> messageQueue;
    private final TelegramBotService service;
    private Thread messageThread;

    public MessageSendQueue(TelegramBotService manager) {
        messageQueue = new ConcurrentLinkedDeque<>();
        this.service = manager;
        messageThread = null;
    }

    public void addMessage(MessageForm form) {
        messageQueue.add(form);
    }

    public void run() {
        messageThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    MessageForm form = messageQueue.poll();
                    if (form != null && service.isRunning()) {
                        if (form.isKakao())
                            SendMessageUtil.sendToKakaoTalk(form.getRoomName(), form.getMessage());
                        else
                            SendMessageUtil.sendToTelegram(form.getRoomId(), form.getMessage());
                    }
                    Thread.sleep(1000);
                }
            }
            catch (Exception e) {

            }
        });
        messageThread.start();
    }

    public void stop() {
        if (messageThread != null)
            messageThread.interrupt();
    }
}
