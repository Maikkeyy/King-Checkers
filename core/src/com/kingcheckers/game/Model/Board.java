package com.kingcheckers.game.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Maikkeyy on 7-5-2017.
 */
public class Board {
    public static final int BOARD_ROWS = 10;
    public static final int BOARD_COLUMNS = 10;
    private int boxWidth = 50;
    private int boxHeight = 50;
    private int boardWidth;
    private int boardHeight;
    private int[][] boxes;

    public Board() {}

    public void loadFromFile(String boardName) {
        /* The board and checkers are loaded from a text file
        *  A text file with numbers representing certain elements is an easy way to mimick a board */
        FileHandle file = Gdx.files.internal("Boards/" + boardName);

        /* Reading board in and split the lines, representing rows */
        String lines[] = file.readString().split("#END");
        for(int i = 0; i < lines.length; ++i) {
            lines[i] = lines[i].replaceAll("\\s", ""); // Remove white chars
        }

        boardHeight = lines.length;
        boardWidth = lines[0].length();

        boxes = new int[boardHeight][boardWidth];

        for(int y = 0; y < boardHeight; ++y) {
            for(int x = 0; x < boardWidth; ++x) {
                /* Converting string values to int values and put them in box array */
                boxes[x][y] = Character.getNumericValue(lines[y].charAt(x));
            }
        }
    }

    public int[][] getBoxes() {
        return this.boxes;
    }

    public int getWidth() { return boardWidth; }
    public int getHeight() { return boardHeight; }

    public void setValue(BoardPosition pos, int val) {
        setValue(pos.x, pos.y, val);
    }

    public void setValue(int x, int y, int val) {
        if(getValue(x, y) != -1) {
            boxes[x][y] = val;
        }
    }

    public int getValue(BoardPosition pos) { return getValue(pos.x, pos.y); }

    public PlayerSide getCheckerPlayer(BoardPosition pos) { return getCheckerPlayer(pos.x, pos.y); }

    public PlayerSide getCheckerPlayer(int x, int y) {
        if(getValue(x,y) == 2 || getValue(x,y) == 4) return PlayerSide.BEIGE;
        else return PlayerSide.BROWN;
    }

    public int getValue(int x, int y) {
        if(x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
            return boxes[x][y];
        }
        else return -1;
    }
}

