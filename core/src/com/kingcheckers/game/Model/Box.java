package com.kingcheckers.game.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by Maikkeyy on 7-5-2017.
 */
public class Box {
    private Color color;
    private ActiveChecker activeChecker;
    private int x;
    private int y;
    private int width;
    private int height;
    private BoundingBox boundingBox;
    private boolean clicked;

    public Box(int x, int y, int width, int height, Color color) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.boundingBox = new BoundingBox(new Vector3(x, y, 0), new Vector3(x + width, y + height, 0));
        this.clicked = false;
    }

    public Color getColor() {
        return this.color;
    }

    public void setActiveChecker(ActiveChecker activeChecker) {
        this.activeChecker = activeChecker;
    }

    public ActiveChecker getActiveChecker() {
        return this.activeChecker;
    }

    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean getClicked() {
        return this.clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void calculateMoves(Board board) {
        int r = 0;
        int c = 0;

        int[][] range;

        for (int row = 0; row < board.getBoxes().length; row++) {
            for (int col = 0; col < board.getBoxes()[row].length; col++) {
                Box b = board.getBoxes()[row][col];

                if(b.getX() == getX() && b.getY()  == getY()) {
                    r = row;
                    c = col;


                }

               // range = new int[][]
            }
        }
    }

    public void removeChecker() {
        this.activeChecker = null;
    }
}
