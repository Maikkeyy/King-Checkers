package com.kingcheckers.game.Model;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Maikkeyy on 7-5-2017.
 */
public class Box {
    private Color color;
    private Piece piece;

    public Box(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

}
