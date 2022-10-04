package com.division.telegramtokakao.manager;

import com.division.telegramtokakao.bot.TelegramBot;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.service.TelegramBotService;
import it.tdlight.common.Init;
import it.tdlight.common.utils.CantLoadLibrary;

import java.util.ArrayList;
import java.util.List;

import static com.division.telegramtokakao.util.SendMessageUtil.injectManager;

public class TelegramManager {

    private final DataManager manager;
    private final List<String> tokenList; //token 비교옹
    private final List<TelegramBot> sessions;
    private TelegramBotService service;

    public TelegramManager(DataManager manager) {
        this.manager = manager;
        this.tokenList = new ArrayList<>();
        this.sessions = new ArrayList<>();
        try {
            Init.start();
        }
        catch (CantLoadLibrary e) {
            Logger.log(e.getMessage());
        }
        injectManager(this);
    }

    public void addBot(String token, int apiHash) {
        if (!tokenList.contains(token)) {
            try {
                tokenList.add(token);
                TelegramBot session = new TelegramBot(token, manager, TelegramBotService.sendQueue, apiHash);
                sessions.add(session);
                Logger.log("[정보] 텔레그램 봇 동작중 : " + token);
                //blocking
                session.startClient();

            }
            catch (Exception e) {
                Logger.log(e.getMessage());
            }
        }
    }

    public void clear() {
        sessions.clear();
        tokenList.clear();
    }
    public List<TelegramBot> getBotList() {
        return sessions;
    }


}
