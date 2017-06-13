package com.kingcheckers.game.Model;

import com.kingcheckers.game.Screen.BattleScreen;

/**
 * Created by Maikkeyy on 31-5-2017.
 */
public class Player {
    private BattleScreen screen;
    private PlayerSide playerSide;

    public Player(BattleScreen screen) {
        this.screen = screen;
    }

    public PlayerSide getPlayerSide() {
        return this.playerSide;
    }

    public void setPlayerSide(PlayerSide playerSide) {
        this.playerSide = playerSide;
    }
}
