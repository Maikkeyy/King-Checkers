package com.kingcheckers.game.Model;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Maikkeyy on 7-5-2017.
 */
public class Board {
    public static final int BOARD_ROWS = 10;
    public static final int BOARD_COLUMNS = 10;
    private int boxWidth = 50;
    private int boxHeight = 50;
    private Box[][] boxes;

    public Board() {
        boxes = new Box[BOARD_ROWS][BOARD_COLUMNS];
    }

    public void initializeBoard() {
        int x = 0;
        int y = 0;
        for (int row = 0; row <= BOARD_ROWS - 1; row++) {
            for (int column = 0; column <= BOARD_COLUMNS - 1; column++) {

                    if (row % 2 == 0) { // even rows
                        if(column % 2 == 0) {
                            boxes[row][column] = new Box(x, y, boxWidth, boxHeight, Color.WHITE);
                            x = x + 50;
                        }
                        else {
                            boxes[row][column] = new Box(x, y, boxWidth, boxHeight, Color.BLACK);
                            x = x + 50;
                        }

                    } else { // uneven rows
                        if(column % 2 == 1) {
                            boxes[row][column] = new Box(x, y, boxWidth, boxHeight, Color.WHITE);
                            x = x + 50;
                        }
                        else {
                            boxes[row][column] = new Box(x, y, boxWidth, boxHeight, Color.BLACK);
                            x = x + 50;
                        }
                    }

                    if(column == 9) {
                        y = y + 50;
                        x = 0;
                    }
                }
            }

            for (int row = 0; row <= BOARD_ROWS - 1; row++) {
                for (int column = 0; column <= BOARD_COLUMNS - 1; column++) {
                    Box b = boxes[row][column];

                    if(row < 4) {
                        if(b.getColor() == Color.BLACK) {
                            b.setChecker(new Checker(CheckerType.WHITE_REGULAR));
                        }
                    }
                    else if(row > 5) {
                        if(b.getColor() == Color.BLACK) {
                            b.setChecker(new Checker(CheckerType.BLACK_REGULAR));
                        }
                    }

                }
            }

        }

    public Box[][] getBoxes() {
        return this.boxes;
    }
}

