package com.kingcheckers.game.RMI;

import java.io.Serializable;

/**
 * Created by Maikkeyy on 16-6-2017.
 */
public class MoveEvent implements Serializable {
    private double oldPosX;
    private double oldPosY;
    private double newPosX;
    private double newPosY;

    public MoveEvent(double oldPosX, double oldPosY, double newPosX, double newPosY) {
        this.oldPosX = oldPosX;
        this.oldPosY = oldPosY;
        this.newPosX = newPosX;
        this.newPosY = newPosY;
    }

    public double getOldPosX() {
        return this.oldPosX;
    }

    public double getOldPosY() {
        return this.oldPosY;
    }

    public double getNewPosX() {
        return this.newPosX;
    }

    public double getNewPosY() {
        return this.newPosY;
    }

}
