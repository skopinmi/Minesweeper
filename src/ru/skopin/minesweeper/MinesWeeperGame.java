package ru.skopin.minesweeper;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class MinesWeeperGame extends JFrame {

    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private static final GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private static boolean isGameStopped;
    private static int countClosedTiles = SIDE * SIDE;
    private static int score;


    private static int countMinesOnField;
    private static int countFlags;

    public static void main(String[] args) {
        MinesWeeperGame minesWeeperGame = new MinesWeeperGame();
        GameWindow.getInstance().setVisible(true);
        minesWeeperGame.restartGame();
    }


    public void restartGame() {
        setGameStopped(false);
        setCountClosedTiles(SIDE * SIDE);
        setCountMinesOnField(0);
        setCountFlags(0);
        setScore(0);
        GameWindow.setTitleGame("" + getScore());
        createGame();
    }

    public int getScore() {
        return score;
    }

    public void setGameStopped(boolean gameStopped) {
        isGameStopped = gameStopped;
    }

    public void setCountClosedTiles(int countClosedTiles) {
        this.countClosedTiles = countClosedTiles;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCountMinesOnField(int countMinesOnField) {
        this.countMinesOnField = countMinesOnField;
    }

    public void setCountFlags(int countFlags) {
        this.countFlags = countFlags;
    }

    private static void win() {
        isGameStopped = true;
        MessageWindow.getInstance();  // Вы выиграли
    }

    private static void gameOver() {
        isGameStopped = true;
        MessageWindow.getInstance(); //"Ваша игра закончена"
    }

    public static void markTile (int x, int y) {

        if(!gameField[y][x].isOpen && countFlags > 0 && !gameField[y][x].isFlag && !isGameStopped) {
            gameField[y][x].isFlag = true;
            GameWindow.setButtonText(x, y, FLAG);
            countFlags--;
        } else if (gameField[y][x].isFlag) {
            gameField[y][x].isFlag = false;
            GameWindow.setButtonText(x, y, "");
            countFlags++;
        }
    }

    public static void openTile(int x, int y){

        if (!gameField[y][x].isOpen && !isGameStopped && !gameField[y][x].isFlag) {
            gameField[y][x].isOpen = true;

            if (gameField[y][x].isMine) {
                GameWindow.setButtonText(x, y, MINE);
                gameOver();
            }
            else {
                score += 5;
                GameWindow.setTitleGame(" " + score);
                countClosedTiles--;
                if (gameField[y][x].countMineNeighbors == 0) {
                    List<GameObject> array;
                    array = getNeighbors(gameField[y][x]);
                    GameWindow.setButtonText(x, y, "*");
                    for(GameObject a : array) {
                        openTile(a.x, a.y);
                    }
                } else {
                    GameWindow.setButtonText(x, y, "" + gameField[y][x].countMineNeighbors);
                }
                if (countClosedTiles == countMinesOnField && countFlags == countMinesOnField) {
                    win();
                }
            }
        }
    }

    public static int getSIDE() {
        return SIDE;
    }

    private static GameObject[][] getGameField() {
        return gameField;
    }

    private void countMineNeighbors() {
        for(GameObject[] a : gameField) {
            for (GameObject b : a) {
                if (!b.isMine) {
                    List<GameObject> array =  getNeighbors(b);
                    for(GameObject c : array) {
                        if (c.isMine) {
                            b.countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private int getRandomNumber (int i) {
        return (int) (Math.random() * i);
    }

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                GameWindow.setButtonText(x, y, "");
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private static List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }
}

