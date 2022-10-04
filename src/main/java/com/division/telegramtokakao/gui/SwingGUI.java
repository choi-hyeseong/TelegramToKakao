package com.division.telegramtokakao.gui;

import com.division.telegramtokakao.data.TelegramSendData;
import com.division.telegramtokakao.file.FileManage;
import com.division.telegramtokakao.listener.ButtonActionListener;
import com.division.telegramtokakao.listener.ButtonAddListener;
import com.division.telegramtokakao.listener.ButtonRemoveListener;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.manager.DataManager;
import com.division.telegramtokakao.service.TelegramBotService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SwingGUI extends JFrame {

    private final TelegramBotService service;

    private DataManager manager;
    private FileManage fileManage;
    public static DefaultListModel<String>[] models = new DefaultListModel[3];

    public SwingGUI(TelegramBotService service, DataManager manager, FileManage manage) {
        this.service = service;
        this.manager = manager;
        this.fileManage = manage;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //구분선
        g.drawLine(20, 120, 580, 120);
        g.drawLine(30, 300, 570, 300);
        g.drawLine(30, 480, 570, 480);
        g.drawLine(30, 660, 570, 660);
    }
    //gui setup
    public void init() {
        setTitle("Telegram Message Sender");
        setSize(600,900);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fileManage.save();
            }
        });
        Container container = getContentPane();
        container.setLayout(null);
        JPanel mainPanel = createControlPanel();
        mainPanel.setBounds(0,0, 600, 100);
        JPanel readPanel = createReadChatPanel();
        readPanel.setBounds(0,100, 600, 150);
        JPanel sendPanel = createSendChatPanel();
        sendPanel.setBounds(0,280, 600, 150);
        JPanel periodPanel = createPeriodPanel();
        periodPanel.setBounds(0,460, 600, 150);
        JPanel logPanel = createLogPanel();
        logPanel.setBounds(0,640, 600, 220);
        container.add(mainPanel);
        container.add(readPanel);
        container.add(sendPanel);
        container.add(periodPanel);
        container.add(logPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(null);
        JButton startButton = new JButton("start");
        JButton stopButton = new JButton("stop");
        JLabel label = new JLabel("서비스 실행 상태 : " + service.isRunning());
        label.setFont(new Font("바탕", Font.BOLD , 15));
        label.setBounds(210,10,200,30);
        startButton.setBounds(150,40, 100, 30);
        startButton.setBackground(new Color(200, 200, 200));
        stopButton.setBackground(new Color(200,200,200));
        stopButton.setBounds(350,40, 100, 30);
        ButtonActionListener listener = new ButtonActionListener(label, startButton, stopButton, service);
        startButton.addActionListener(listener);
        stopButton.addActionListener(listener);
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(label);
        return controlPanel;
    }

    private JPanel createReadChatPanel() {
        JPanel readPanel = new JPanel(null);
        JLabel label = new JLabel("메시지를 읽어올 채팅방 정보");
        label.setFont(new Font("바탕", Font.PLAIN , 13));
        label.setBounds(20,0, 200,20);
        label.setSize(200,20);
        DefaultListModel<String> data = new DefaultListModel<>();
        JList<String> list = new JList<>(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20,25, 500,125);
        scrollPane.setViewportView(list);
        JButton addButton = new JButton("+");
        JButton removeButton = new JButton("-");
        addButton.setBounds(525, 25, 30,30);
        removeButton.setBounds(525, 60, 30, 30);
        addButton.setBackground(new Color(200, 200, 200));
        removeButton.setBackground(new Color(200,200,200));
        addButton.setMargin(new Insets(0, 0, 0, 0));
        removeButton.setMargin(new Insets(0, 0, 0, 0));
        addButton.setFont(new Font("바탕", Font.PLAIN , 10));
        removeButton.setFont(new Font("바탕", Font.PLAIN , 10));
        addButton.addActionListener(new ButtonAddListener(manager,this, 1, data));
        readPanel.add(label);
        readPanel.add(scrollPane);
        readPanel.add(addButton);
        readPanel.add(removeButton);
        removeButton.addActionListener(new ButtonRemoveListener(1, data, list, manager));
        models[0] = data;
        return readPanel;
    }

    private JPanel createSendChatPanel() {
        JPanel sendPanel = new JPanel(null);
        JLabel label = new JLabel("메시지를 전송할 채팅방 정보");
        label.setFont(new Font("바탕", Font.PLAIN , 13));
        label.setBounds(20,0, 200,20);
        label.setSize(200,20);
        DefaultListModel<String> data = new DefaultListModel<>();
        JList<String> list = new JList<>(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        scrollPane.setBounds(20,25, 500,125);
        JButton addButton = new JButton("+");
        JButton removeButton = new JButton("-");
        addButton.setBounds(525, 25, 30,30);
        removeButton.setBounds(525, 60, 30, 30);
        addButton.setBackground(new Color(200, 200, 200));
        removeButton.setBackground(new Color(200,200,200));
        addButton.setMargin(new Insets(0, 0, 0, 0));
        removeButton.setMargin(new Insets(0, 0, 0, 0));
        addButton.setFont(new Font("바탕", Font.PLAIN , 10));
        removeButton.setFont(new Font("바탕", Font.PLAIN , 10));
        addButton.addActionListener(new ButtonAddListener(manager, this, 2, data));
        sendPanel.add(label);
        sendPanel.add(scrollPane);
        sendPanel.add(addButton);
        sendPanel.add(removeButton);
        removeButton.addActionListener(new ButtonRemoveListener(2, data, list, manager));
        models[1] = data;
        return sendPanel;
    }

    private JPanel createPeriodPanel() {
        JPanel periodPanel = new JPanel(null);
        JLabel label = new JLabel("주기적 메시지 전송 정보");
        label.setFont(new Font("바탕", Font.PLAIN , 13));
        label.setBounds(20,0, 200,20);
        label.setSize(200,20);
        DefaultListModel<String> data = new DefaultListModel<>();
        JList<String> list = new JList<>(data);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20,25, 500,125);
        scrollPane.setViewportView(list);
        JButton addButton = new JButton("+");
        JButton removeButton = new JButton("-");
        addButton.setBounds(525, 25, 30,30);
        removeButton.setBounds(525, 60, 30, 30);
        addButton.setBackground(new Color(200, 200, 200));
        removeButton.setBackground(new Color(200,200,200));
        addButton.setMargin(new Insets(0, 0, 0, 0));
        removeButton.setMargin(new Insets(0, 0, 0, 0));
        addButton.setFont(new Font("바탕", Font.PLAIN , 10));
        removeButton.setFont(new Font("바탕", Font.PLAIN , 10));
        addButton.addActionListener(new ButtonAddListener(manager, this, 3, data));
        periodPanel.add(label);
        periodPanel.add(scrollPane);
        periodPanel.add(addButton);
        periodPanel.add(removeButton);
        removeButton.addActionListener(new ButtonRemoveListener(3, data, list, manager));
        models[2] = data;
        return periodPanel;
    }

    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(null);
        JLabel label = new JLabel("로그");
        label.setFont(new Font("바탕", Font.PLAIN , 13));
        label.setBounds(20,0, 200,20);
        label.setSize(200,20);
        JTextArea text = new JTextArea();
        text.setEditable(false);
        Logger.area = text;
        JScrollPane scrollPane = new JScrollPane();
        text.setBounds(20,25, 550, 200);
        text.setFont(new Font("바탕", Font.PLAIN , 13));
        scrollPane.setBounds(20,25, 550,180);
        scrollPane.setViewportView(text);
        logPanel.add(label);
        logPanel.add(scrollPane);
        return logPanel;
    }

}
