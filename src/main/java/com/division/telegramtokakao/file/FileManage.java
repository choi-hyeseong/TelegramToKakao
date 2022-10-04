package com.division.telegramtokakao.file;

import com.division.telegramtokakao.data.*;
import com.division.telegramtokakao.gui.SwingGUI;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.manager.DataManager;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;

public class FileManage {

    private File file;
    private DataManager manager;


    public FileManage(DataManager manager) {
        this.manager = manager;
        file = new File("data.dat");
    }

    public void load() {
        try {
            if (!file.exists())
                file.createNewFile();
            else {
                DefaultListModel<String>[] models = SwingGUI.models;
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String read = reader.readLine();
                while (read != null) {
                    String[] split = read.split("\\|");
                    switch (split[0]) {
                        case "TS" -> {
                            long id = Long.parseLong(split[1]);
                            int channel = Integer.parseInt(split[2]);
                            String token = split[3];
                            int apiHash = Integer.parseInt(split[4]);
                            manager.addSendData(token, id, channel, apiHash);
                            models[0].add(models[0].size(), "[ 채널 : " + channel + "] " + "텔레그램 id : " + id + " api 해쉬 : " + apiHash);
                        }
                        case "TR" -> {
                            TelegramReceiveData data;
                            long roomId = Long.parseLong(split[1]);
                            int channels = Integer.parseInt(split[2]);
                            String filter = "";
                            if (split.length == 4) {
                                filter = split[3];
                                data = new TelegramReceiveData(roomId, channels, filter);
                            }
                            else
                                data = new TelegramReceiveData(roomId, channels);
                            manager.addReceiveData(data);
                            models[1].add(models[1].size(), "[ 채널 : " + channels + "] " + "정보 : " + roomId + " 카카오톡 유뮤 : false" + " 필터 : " + filter);
                        }
                        case "KR" -> {
                            KakaoTalkData data;
                            int kakaoChan = Integer.parseInt(split[1]);
                            String chat = split[2];
                            String filter = "";
                            if (split.length == 4) {
                                filter = split[3];
                                data = new KakaoTalkData(chat, kakaoChan, filter);
                            }
                            else
                                data = new KakaoTalkData(chat, kakaoChan);
                            manager.addReceiveData(data);
                            models[1].add(models[1].size(), "[ 채널 : " + kakaoChan + "] " + "정보 : " + chat + " 카카오톡 유뮤 : true" + " 필터 : " + filter);
                        }
                        case "PM" -> {
                            String room = split[1];
                            int period = Integer.parseInt(split[2]);
                            String msg = split[3].replace("<줄바꿈>", "\n");
                            manager.addPeriodData(room, msg, period);
                            models[2].add(models[2].size(), "[ 채팅방 : " + room + "] " + "메시지 : " + msg + " 주기 : " + period + "초");
                        }
                    }
                    read = reader.readLine();
                }
                reader.close();
            }
        }
        catch (Exception e) {
            Logger.log("[경고] 데이터 로딩 실패 : " + e.getMessage());
        }
    }

    public void save() {
        try {
            if (file.exists())
                file.delete();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (TelegramSendData data : manager.getSendData()) {
                writer.write("TS|" + data.getChatID() + "|" + data.getChannel() + "|" + data.getBotToken()  + "|" + data.getApiHash() + "\n");
            }
            for (ReceiveData data : manager.getReceiveData()) {
                String filter = data.getFilter() == null ? "" : "|" + data.getFilter();
                if (data instanceof TelegramReceiveData receiveData) {
                    writer.write("TR|" + receiveData.getChatID() + "|" + receiveData.getChannel() + filter + "\n");
                }
                else {
                    KakaoTalkData kakaoTalkData = (KakaoTalkData) data;
                    writer.write("KR|" + kakaoTalkData.getChannel() + "|" + kakaoTalkData.getChatName() + filter + "\n");
                }
            }
            for (PeriodMessageData data : manager.getPeriodData()) {
                writer.write("PM|" + data.getRoomName() + "|" + data.getPeriod() + "|" + data.getMessage().replace("\n", "<줄바꿈>") + "\n");
            }
            writer.flush();
            writer.close();
        }

        catch (Exception e) {
            Logger.log("[경고] 데이터 로딩 실패 : " + e.getMessage());
        }
    }
}
