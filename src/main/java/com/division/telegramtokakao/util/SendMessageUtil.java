package com.division.telegramtokakao.util;

import com.division.telegramtokakao.bot.TelegramBot;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.manager.TelegramManager;
import com.division.telegramtokakao.unsafe.JNAccess;
import it.tdlight.jni.TdApi;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SendMessageUtil {

    private static TelegramManager manager;

    public static void injectManager(TelegramManager value) {
        manager = value;
    }

    public static void sendToTelegram(long roomId, String message) {
        List<TelegramBot> list = new ArrayList<>(manager.getBotList());
        for (TelegramBot bot : list) {
            bot.getClient().send(new TdApi.SendMessage(roomId, 0, 0, null, null, new TdApi.InputMessageText(new TdApi.FormattedText(message, null), false, false)), result -> {});
        }
        if (list.size() == 0)
            Logger.log("[오류] 메시지 전송 실패 : 텔레그램 방 id : " + roomId + " 메시지 : " + message);
    }

    public static void sendToKakaoTalk(String roomName, String message) {
        if (JNAccess.focusWindow(roomName)) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(message);
            clipboard.setContents(selection, selection);
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_V);
                Thread.sleep(400);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_V);
                robot.keyPress(KeyEvent.VK_ENTER);
                Thread.sleep(400);
                robot.keyRelease(KeyEvent.VK_ENTER);
            }
            catch (InterruptedException | AWTException e) {
                Logger.log("[오류] 카카오톡 메시지 전송실패 " + e.getMessage());
            }


        }

    }
}
