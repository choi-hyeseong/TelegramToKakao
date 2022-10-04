package com.division.telegramtokakao.manager;

import com.division.telegramtokakao.data.MessageForm;
import com.division.telegramtokakao.data.PeriodMessageData;
import com.division.telegramtokakao.service.MessageSendQueue;
import com.division.telegramtokakao.util.SendMessageUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PeriodMessageSender {

    private Thread thread;
    private DataManager manager;
    private MessageSendQueue queue;

    public PeriodMessageSender(DataManager manager, MessageSendQueue queue) {
        this.manager = manager;
        this.queue = queue;
    }

    public void start() {
        thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    List<PeriodMessageData> list = manager.getPeriodData();
                    synchronized (list) {
                        for (PeriodMessageData data : list) {
                            int period = data.getPeriod() - 1;
                            if (period <= 0) {
                                data.resetPeriod();
                                queue.addMessage(new MessageForm(data.getMessage(), true, -1, data.getRoomName()));
                                //SendMessageUtil.sendToKakaoTalk(data.getRoomName(), data.getMessage()); 메시지 큐 사용
                            }
                            else
                                data.setPeriod(period);
                        }
                    }
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException e) {

            }
        });
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }
}
