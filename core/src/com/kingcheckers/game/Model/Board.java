package com.kingcheckers.game.Model;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Maikkeyy on 7-5-2017.
 */
public class Board {
    public static final int BOARD_ROWS = 10;
    public static final int BOARD_COLUMNS = 10;
    private Box[][] boxes;

    public Board() {
        boxes = new Box[BOARD_ROWS][BOARD_COLUMNS];
    }

    public void initializeBoard() {
        for (int row = 0; row <= BOARD_ROWS - 1; row++) {
            for (int column = 0; column <= BOARD_COLUMNS - 1; column++) {
                    if (row % 2 == 0) { // even rows
                        if(column % 2 == 0) {
                            boxes[row][column] = new Box(Color.WHITE);
                        }
                        else {
                            boxes[row][column] = new Box(Color.BLACK);
                        }

                    } else {
                        if(column % 2 == 1) { // uneven rows
                            boxes[row][column] = new Box(Color.WHITE);
                        }
                        else {
                            boxes[row][column] = new Box(Color.BLACK);
                        }
                    }
                }
            }
        }
}

