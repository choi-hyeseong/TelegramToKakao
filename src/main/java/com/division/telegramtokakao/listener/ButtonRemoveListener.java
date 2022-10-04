package com.division.telegramtokakao.listener;

import com.division.telegramtokakao.data.TelegramSendData;
import com.division.telegramtokakao.logger.Logger;
import com.division.telegramtokakao.manager.DataManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonRemoveListener implements ActionListener {

    private final int index;
    private final DefaultListModel<String> data;
    private final JList<String> dataIndex;
    private final DataManager manager;

    public ButtonRemoveListener(int index, DefaultListModel<String> data, JList<String> dataIndex, DataManager manager) {
        this.index = index;
        this.dataIndex = dataIndex;
        this.data = data;
        this.manager = manager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dataIndex.getSelectedIndex() != -1) {
            int selectedValue = dataIndex.getSelectedIndex();
            if (index == 1) {
                //수신정보
                manager.getSendData().remove(selectedValue);
                Logger.log("[삭제] " + "수신 채널 삭제됨. 인덱스 : " + selectedValue);
            }
            else if (index == 2) {
                //송신정보
                manager.getReceiveData().remove(selectedValue);
                Logger.log("[삭제] " + "송신 채널 삭제됨. 인덱스 : " + selectedValue);
            }
            else if (index == 3) {
                manager.getPeriodData().remove(selectedValue);
                Logger.log("[삭제] " + "정기 메시지 삭제됨. 인덱스 : " + selectedValue);
            }
            data.removeElementAt(selectedValue);

        }
    }
}
