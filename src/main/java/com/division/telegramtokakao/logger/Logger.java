package com.division.telegramtokakao.logger;

import javax.swing.*;
import java.awt.*;

public class Logger {

    public static JTextArea area = null;

    public static void log(String value) {
        if (area != null)
            area.append(value + "\n");
    }
}
