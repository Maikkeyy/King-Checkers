package com.kingcheckers.game.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/**
 * Created by Maikkeyy on 7-5-2017.
 */
public class Box {
    private Color color;
    private Checker checker;
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

    public void setChecker(Checker checker) {
        this.checker = checker;
    }

    public Checker getChecker() {
        return this.checker;
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

}
