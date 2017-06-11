package com.kingcheckers.game.Model;

import com.kingcheckers.game.Screen.GameScreen;

/**
 * Created by Maikkeyy on 31-5-2017.
 */
public class Player {
    private GameScreen screen;
    private PlayerSide playerSide;

    public Player(GameScreen screen) {
        this.screen = screen;
    }

    public PlayerSide getPlayerSide() {
        return this.playerSide;
    }

    public void setPlayerSide(PlayerSide playerSide) {
        this.playerSide = playerSide;
    }
}
