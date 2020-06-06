package ru.skopin.minesweeper;

import javax.swing.*;

public class MessageWindow extends JFrame {
    private static MessageWindow messageWindow;
    private MessageWindow () {
        setTitle("Сообщение");
        setBounds(10, 10, 400, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public static MessageWindow getInstance() {
        if (messageWindow == null) {
            messageWindow = new MessageWindow();
        }
        return messageWindow;
    }
}
