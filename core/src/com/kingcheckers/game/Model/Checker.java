package com.kingcheckers.game.Model;

/**
 * Created by Maikkeyy on 31-5-2017.
 */
public class Checker {
    private CheckerType checkerType;

    public Checker(CheckerType checkerType) {
        this.checkerType = checkerType;
    }

    public CheckerType getCheckerType() {
        return this.checkerType;
    }
}
