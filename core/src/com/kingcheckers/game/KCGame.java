package com.kingcheckers.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kingcheckers.game.Screen.StartScreen;

/**
 * Created by Mike on 24-5-2017.
 */
public class KCGame extends Game {
    public static Skin skin;

    @Override
    public void create() {
        this.skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
        this.setScreen(new StartScreen(this));
    }

}
