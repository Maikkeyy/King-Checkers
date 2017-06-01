package com.kingcheckers.game.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kingcheckers.game.KCGame;

/**
 * Created by Mike on 24-5-2017.
 */
public class StartScreen implements Screen {
    private Stage stage;
    private Table table;
    private Game game;

    public StartScreen(Game game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = KCGame.skin;

        table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        Label lblTitle = new Label("King Checkers", skin);
        table.add(lblTitle);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
