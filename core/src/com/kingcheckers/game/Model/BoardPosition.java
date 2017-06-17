package com.kingcheckers.game.Model;

import java.io.Serializable;

/**
 * Created by Maikkeyy on 1-6-2017.
 */
public class BoardPosition implements Serializable {
    public int x, y;

    public BoardPosition() { this.setPosition(0,0); }
    public BoardPosition(int x, int y) { setPosition(x, y); }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static BoardPosition getDistance(BoardPosition posA, BoardPosition posB) {
        // Retrieving the absolute value of the distance between two points, which is the positive of any number
        int distX = Math.abs(posA.x - posB.x);
        int distY = Math.abs(posA.y - posB.y);

        return new BoardPosition(distX, distY);
    }

    public static BoardPosition getDirection(BoardPosition posA, BoardPosition posB) {
        // Retrieving the value of the distance between two points, considering - as left and + as right
        int distX = posA.x - posB.x;
        int distY = posA.y - posB.y;

        return new BoardPosition(distX, distY);
    }

    public String toString() { return "[" + x + "," + y + "]"; }
    public boolean isEqual(BoardPosition pos) {
        return isEqual(pos.x, pos.y);
    }

    public boolean isEqual(int x, int y) {
        return this.x == x && this.y == y;
    }
}
