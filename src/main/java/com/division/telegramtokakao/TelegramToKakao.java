package com.division.telegramtokakao;

import com.division.telegramtokakao.file.FileManage;
import com.division.telegramtokakao.gui.SwingGUI;
import com.division.telegramtokakao.manager.DataManager;
import com.division.telegramtokakao.manager.TelegramManager;
import com.division.telegramtokakao.service.TelegramBotService;

public class TelegramToKakao {

    public static void main(String[] args) {
        DataManager manager = new DataManager();
        TelegramManager telegramManager = new TelegramManager(manager);
        TelegramBotService service = new TelegramBotService(telegramManager, manager);
        FileManage manage = new FileManage(manager);
        service.run();
        SwingGUI gui = new SwingGUI(service, manager, manage);

        gui.init();
        manage.load();

    }
}
