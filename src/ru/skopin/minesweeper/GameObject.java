package ru.skopin.minesweeper;

public class GameObject {

    public boolean isMine;
    public boolean isFlag;
    public boolean isOpen;

    public int x;
    public int y;
    public int countMineNeighbors;




    public GameObject(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }
}

