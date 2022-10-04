package com.division.telegramtokakao.gui;

import com.division.telegramtokakao.data.KakaoTalkData;
import com.division.telegramtokakao.data.ReceiveData;
import com.division.telegramtokakao.data.TelegramReceiveData;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.manager.DataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class AddGUI extends JDialog {

    private DataManager manager;

    public AddGUI(DataManager manager, Frame parent, int index, DefaultListModel<String> data) {
        super(parent, "채팅방 추가", true);
        this.manager = manager;
        setLayout(null);
        setSize(400, 250);
        setLocation((int)parent.getLocation().getX() + 150, (int)parent.getLocation().getY() + 200);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JButton ok = new JButton("추가");
        JButton no = new JButton("취소");
        if (index == 1) {
            JLabel token = new JLabel("api 해쉬 : ");
            JLabel hashLabel = new JLabel("봇 토큰 : ");
            token.setBounds(10, 10, 100, 20);
            JTextField tokenArea = new JTextField();
            JTextField idArea = new JTextField();
            JTextField hashArea = new JTextField();
            JLabel id = new JLabel("텔레그램 방 id : ");
            JLabel channel = new JLabel("채널 : ");
            JTextField channelArea = new JTextField();
            id.setBounds(10, 20, 100, 50);
            hashLabel.setBounds(10, 80, 100, 20);
            tokenArea.setBounds(120, 10, 250, 20);
            idArea.setBounds(120, 40, 250, 20);
            channel.setBounds(10, 60, 100, 20);
            channelArea.setBounds(120, 65, 250 ,20);
            hashArea.setBounds(120, 90, 250 ,20);
            add(channel);
            add(channelArea);
            add(token);
            add(id);
            add(tokenArea);
            add(hashArea);
            add(hashLabel);
            add(idArea);
            ok.addActionListener((e) -> {
                try {
                    String tok = tokenArea.getText();
                    long roomId = Long.parseLong(idArea.getText());
                    int channelId = Integer.parseInt(channelArea.getText());
                    int hash = Integer.parseInt(hashArea.getText());
                    manager.addSendData(tok, roomId, channelId, hash);
                    data.add(data.size(), "[ 채널 : " + channelId + "] " + "텔레그램 id : " + roomId);
                    Logger.log("[수신 채널 생성] 채널 : " + channelId + " 텔레그램 id : " + roomId + " 토큰값 : " + tok);
                }
                catch (NumberFormatException ex) {
                    Logger.log("[오류] : " + ex.getMessage());
                }
                dispose();
            });
            ok.setBounds(50, 120, 100, 30);
            no.setBounds(230, 120, 100, 30);
        }
        else if (index == 2) {
            JLabel name = new JLabel("채팅방이름 / 방 id : ");
            JLabel isKakaoLabel = new JLabel("카카오톡인경우 체크");
            JCheckBox isKakao = new JCheckBox();
            JLabel hasFilter = new JLabel("필터링할 id :");
            name.setBounds(10, 10, 150, 20);
            JTextField nameArea = new JTextField();
            JTextField idArea = new JTextField();
            JTextField filterArea = new JTextField();
            JLabel id = new JLabel("채널 id : ");
            id.setBounds(10, 20, 100, 50);
            isKakaoLabel.setBounds(10, 60, 150 ,20);
            nameArea.setBounds(150, 10, 200, 20);
            isKakao.setBounds(145, 65, 20, 20);
            idArea.setBounds(150, 40, 200, 20);
            hasFilter.setBounds(10, 90, 150 ,20);
            filterArea.setBounds(150, 90, 200 ,20);
            add(name);
            add(id);
            add(nameArea);
            add(idArea);
            add(isKakao);
            add(isKakaoLabel);
            add(hasFilter);
            add(filterArea);
            ok.addActionListener((e) -> {
                try {
                    String filter = filterArea.getText() == null ? "" : filterArea.getText();
                    String chat = nameArea.getText();
                    long channelId = Long.parseLong(idArea.getText());
                    boolean isKakaoTalk = isKakao.isSelected();
                    ReceiveData addData;
                    if (isKakaoTalk) {
                        addData = new KakaoTalkData(chat, Integer.parseInt(idArea.getText()));
                    }
                    else {
                        addData = new TelegramReceiveData(Long.parseLong(chat), Integer.parseInt(idArea.getText()));
                    }
                    if (filter.length() != 0)
                        addData.setFilter(filter);
                    data.add(data.size(), "[ 채널 : " + channelId + "] " + "정보 : " + chat + " 카카오톡 유뮤 : " + isKakaoTalk + " 필터 : " + filter);
                    Logger.log("[송신 채널 생성] 채널 : " + channelId + " 데이터 : " + chat + " 카카오톡 유뮤 : " + isKakaoTalk + " 필터 : " + filter);
                    manager.addReceiveData(addData);
                }
                catch (NumberFormatException ex) {
                    Logger.log("[오류] : " + ex.getMessage());
                }
                dispose();
            });
            ok.setBounds(50, 120, 100, 30);
            no.setBounds(230, 120, 100, 30);

        }
        else if (index == 3) {
            setSize(400, 300);
            JLabel name = new JLabel("채팅방이름 : ");
            JLabel messageLabel = new JLabel("메시지 : ");
            JScrollPane messagePane = new JScrollPane();
            JLabel periodLabel = new JLabel("주기(초) : ");
            name.setBounds(10, 10, 150, 20);
            JTextField nameArea = new JTextField();
            JTextArea messageArea = new JTextArea(5,1);
            JTextField periodArea = new JTextField();
            messageLabel.setBounds(10, 30, 100, 20);
            periodLabel.setBounds(10, 140, 150 ,20);
            nameArea.setBounds(150, 10, 200, 20);
            messageArea.setBounds(150, 35, 200, 100);
            periodArea.setBounds(150,140,200,20);
            messagePane.setBounds(150,35,200,95);
            messagePane.setViewportView(messageArea);
            add(name);
            add(messageLabel);
            add(nameArea);
            add(periodLabel);
            add(periodArea);
            add(messagePane);
            ok.addActionListener((e) -> {
                try {
                    String chat = nameArea.getText();
                    String message = messageArea.getText();
                    int period = Integer.parseInt(periodArea.getText());
                    data.add(data.size(), "[ 채팅방 : " + chat + "] " + "메시지 : " + message + " 주기 : " + period + "초");
                    Logger.log("[정기 메시지 생성] 채팅방 : " + chat + " 메시지 : " + message.replace("\n", "<줄바꿈>") + " 주기 : " + period + "초");
                    manager.addPeriodData(chat, message, period);
                }
                catch (NumberFormatException ex) {
                    Logger.log("[오류] : " + ex.getMessage());
                }
                dispose();
            });
            ok.setBounds(50, 200, 100, 30);
            no.setBounds(230, 200, 100, 30);
        }


        add(ok);
        add(no);
        no.addActionListener((e) -> dispose());
        setVisible(true);
    }
}
