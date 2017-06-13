package com.kingcheckers.game;

import com.badlogic.gdx.Game;
import com.kingcheckers.game.Screen.BattleScreen;
import com.kingcheckers.game.Screen.MenuScreen;
import com.kingcheckers.game.Screen.StartScreen;

/**
 * Created by Mike on 24-5-2017.
 */
public class KingCheckers extends Game {
    public enum ScreenMode {MENU, START_BATTLE, BATTLE}
    public static String GAME_TITLE = "King Checkers!";
    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    private MenuScreen menu;
    private StartScreen start;
    private BattleScreen battle;

    @Override
    public void create() {
        this.menu = new MenuScreen(this);
        this.battle = new BattleScreen(this);
        this.start = new StartScreen(this);
        this.setScreen(menu);
    }

    public void setScreen(ScreenMode mode) {
        if(mode == ScreenMode.MENU) {
            this.setScreen(menu);
        }
        else if(mode == ScreenMode.BATTLE) {
            this.setScreen(battle);
           // battle.startBattle(start.getPlayerNames(), start.getBoardName());
            String[] playerNames = {"henry", "harrie"};
            battle.startBattle(playerNames, "10x10.txt");
        }
        else if(mode == ScreenMode.START_BATTLE) {
            this.setScreen(start);
        }
    }

}
