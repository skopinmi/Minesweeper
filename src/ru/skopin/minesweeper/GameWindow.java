package ru.skopin.minesweeper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import static java.awt.event.MouseEvent.MOUSE_PRESSED;

public class GameWindow  extends JFrame{
    private static GameWindow gameWindow;
    private static JButton [] jButtons;
    private static String titleGame = "";
    private GameWindow () {
        setTitle("Игра Сапер v 0.1" + titleGame);
        setBounds(10, 10, 500, 500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int countOfButtonsRaw = MinesWeeperGame.getSIDE();
        setLayout(new GridLayout(3, 3));
        JPanel jPanel1 = new JPanel(new GridLayout(3, 3));
        JPanel jPanel2 = new JPanel(new GridLayout(3, 3));
        JPanel jPanel3 = new JPanel(new GridLayout(3, 3));
        int countOfButtonsAll = countOfButtonsRaw * countOfButtonsRaw;
        jButtons = new JButton[countOfButtonsAll];
        for (int i = 0; i < countOfButtonsRaw; i++) {
            for (int ii = 0; ii < countOfButtonsRaw; ii++ ){
                int index = 0;
                if (i == 0) {
                    index = ii;
                } else {
                    index = i * countOfButtonsRaw + ii;
                }
                jButtons[index] = new JButton();
                if (i < 3) {
                    jPanel1.add(jButtons[index]);
                } else if (i < 6) {
                    jPanel2.add(jButtons[index]);
                } else {
                    jPanel3.add(jButtons[index]);
                }
            }
        }
        for (int i = 0; i < countOfButtonsAll; i++) {
            int index = i;
            jButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    openTileInWindow(index);
                }
            });
            jButtons[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton() == 3) {
                        markTileInWindow(mouseEvent.getComponent());
                    }
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {

                }
            });
        }
        add(jPanel1);
        add(jPanel2);
        add(jPanel3);
        setVisible(true);
    }

    public static void setTitleGame (String titleGame) {
        GameWindow.titleGame = titleGame;
    }

    public static GameWindow getInstance() {
        if (gameWindow == null) {
            gameWindow = new GameWindow();
        }
        return gameWindow;
    }
    public static void setButtonText (int x, int y, String line) {
        int side = MinesWeeperGame.getSIDE();
        jButtons[y * side + x].setText(line);
    }
    public static void openTileInWindow (int index) {
        int side = MinesWeeperGame.getSIDE();
        for (int i = 0; i < side; i ++) {
            for (int ii = 0; ii < side; ii++) {
               if (index == i * side + ii) {
                   MinesWeeperGame.openTile(ii, i);
               }
            }
        }
    }
    public static void markTileInWindow (Object object) {
        int side = MinesWeeperGame.getSIDE();
        int index = 0;
        for (int i = 0; i < side * side; i++ ) {
            if (jButtons[i].equals(object)) {
                index = i;
                break;
            }
        }
        for (int i = 0; i < side; i ++) {
            for (int ii = 0; ii < side; ii++) {
                if (index == i * side + ii) {
                    MinesWeeperGame.markTile(ii, i);
                }
            }
        }

    }
}
