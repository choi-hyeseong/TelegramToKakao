package com.division.telegramtokakao.bot;

import com.division.telegramtokakao.data.*;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.manager.DataManager;
import com.division.telegramtokakao.service.MessageSendQueue;
import it.tdlight.client.APIToken;
import it.tdlight.client.AuthenticationData;
import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.client.TDLibSettings;
import it.tdlight.jni.TdApi;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TelegramBot {

    private final String token;
    private final int apiHash;
    private final DataManager manager;
    private final MessageSendQueue queue;
    private SimpleTelegramClient simpleTelegramClient;

    public TelegramBot(String token, DataManager manager, MessageSendQueue sendQueue, int apiHash) {
        this.token = token;
        this.manager = manager;
        this.queue = sendQueue;
        this.apiHash = apiHash;
    }

    public void startClient() {
        SimpleTelegramClient client;
        AuthenticationData data;
        APIToken apiToken = new APIToken(apiHash, token);
        TDLibSettings settings = TDLibSettings.create(apiToken);
        var sessionPath = Paths.get(token + "-example-tdlight-session");
        settings.setDatabaseDirectoryPath(sessionPath.resolve(token + "-data"));
        settings.setDownloadedFilesDirectoryPath(sessionPath.resolve(token + "-downloads"));
        client = new SimpleTelegramClient(settings);
        data = AuthenticationData.consoleLogin();
        client.addUpdateHandler(TdApi.UpdateNewMessage.class, this::onUpdateNewMessage);
        client.start(data);
        simpleTelegramClient = client;
    }

    private void onUpdateNewMessage(TdApi.UpdateNewMessage update) {
        if (update.message != null && update.message.content instanceof TdApi.MessageText text) {
            long senderId;
            if (update.message.senderId instanceof TdApi.MessageSenderUser user)
                senderId = user.userId;
            else if (update.message.senderId instanceof TdApi.MessageSenderChat chat) {
                senderId = chat.chatId;
            }
            else
                senderId = -1;
            Logger.log("[????????? ?????????] ?????? : " + update.message.chatId + " ????????? : " + text.text.text + " ????????? id : " + senderId);
            List<Integer> sendChannels = new ArrayList<>();
            List<ReceiveData> receiveData = new ArrayList<>(); //???????????? ?????????
            for (TelegramSendData data : new ArrayList<>(manager.getSendData())) {
                if (data.getBotToken().equals(token) && !sendChannels.contains(data.getChannel()) && data.getChatID() == update.message.chatId) {
                    sendChannels.add(data.getChannel()); //????????? ?????? ??????
                }
            }

            for (ReceiveData data : new ArrayList<>(manager.getReceiveData())) {
                if (sendChannels.contains(data.getChannel())) {
                    if (data.getFilter() != null && data.getFilter().length() != 0) {
                        //????????? ???????????????
                        String[] filter = data.getFilter().replace(" ", "").split(","); //123, 456
                        for (String str : filter) {
                            try {
                                long id = Long.parseLong(str);
                                if (id == senderId) {
                                    receiveData.add(data);
                                    break;
                                }
                            }
                            catch (NumberFormatException e) {
                                Logger.log("[??????] ?????? ???????????? ????????? ?????? ????????????. " + str);
                            }
                        }
                    }
                    else
                        receiveData.add(data);
                }
            }

            receiveData.forEach(data -> {
                if (data instanceof KakaoTalkData kakaoTalkData) {

                    queue.addMessage(new MessageForm(text.text.text, true, -1, kakaoTalkData.getChatName()));
                }
                else {
                    TelegramReceiveData telegramReceiveData = (TelegramReceiveData) data;
                    queue.addMessage(new MessageForm(text.text.text, false, telegramReceiveData.getChatID(), null));
                }

            });
        }

    }

    public SimpleTelegramClient getClient() {
        return simpleTelegramClient;
    }
}
