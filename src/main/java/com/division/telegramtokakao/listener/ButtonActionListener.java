package com.division.telegramtokakao.listener;

import com.division.telegramtokakao.service.TelegramBotService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

    private final JLabel mainLabel;
    private final JButton startButton;
    private final JButton stopButton;
    private final TelegramBotService service;

    public ButtonActionListener(JLabel label, JButton startButton, JButton stopButton, TelegramBotService service) {
        this.mainLabel = label;
        this.startButton = startButton;
        this.stopButton = stopButton;
        this.service = service;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == startButton)
            service.run();
        else if (source == stopButton)
            service.stop();
        mainLabel.setText("서비스 실행 상태 : " + service.isRunning());
    }
}
