package com.division.telegramtokakao.unsafe;

import com.division.telegramtokakao.logger.Logger;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class JNAccess {

     interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);

        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }

        Pointer FindWindowA(String className, String windowName);

        Pointer GetForegroundWindow();

        boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer userData);

        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);

        Pointer GetWindow(Pointer hWnd, int uCmd);

        Pointer SetFocus(Pointer hWnd);

        int GetWindowRect(Pointer handle, int[] rect);

        boolean SetForegroundWindow(Pointer hWnd);
    }

    public static boolean focusWindow(String name) {
        AtomicBoolean result = new AtomicBoolean(false);
        final User32 user32 = User32.INSTANCE;
        user32.EnumWindows((hWnd, arg) -> {
            byte[] windowText = new byte[512];
            user32.GetWindowTextA(hWnd, windowText, 512);
            try {
                String wText = null;
                wText = new String(windowText, "EUC-KR").trim();
                if (wText.contains(name)) {
                    int[] rect = {0,0,0,0};
                    user32.GetWindowRect(hWnd, rect);
                    boolean val = user32.SetForegroundWindow(hWnd);
                    if (!val) {
                        int x = rect[0];
                        int y = rect[1];
                        int x1 = rect[2];
                        int y1 = rect[3];
                        if (x == -32000 && y == -32000) {
                            Logger.log("[오류 / 채팅방 : "  + name + "] 카카오톡 채팅방 포커싱에 실패하였습니다. 해당 채팅방을 최소화 시키지 말아주세요.");
                        }
                        else {
                            Robot robot = new Robot();
                            robot.mouseMove(x + ((x1 - x) / 2), y + ((y1 - y) / 2));
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        }
                    }


                    result.set(true);
                    //user32.SetFocus(hWnd);
                }
            }
            catch (UnsupportedEncodingException | AWTException e) {

            }
            return true;
        }, null);
        return result.get();

    }

}
