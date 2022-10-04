package com.division.telegramtokakao.listener;

import com.division.telegramtokakao.gui.AddGUI;
import com.division.telegramtokakao.manager.DataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonAddListener implements ActionListener {

    private DataManager manager;
    private final Frame frame;
    private final int index;
    private DefaultListModel<String> model;

    public ButtonAddListener(DataManager manager, Frame frame, int index, DefaultListModel<String> model) {
        this.frame = frame;
        this.index = index;
        this.manager = manager;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new AddGUI(manager, frame, index, model);
    }
}
