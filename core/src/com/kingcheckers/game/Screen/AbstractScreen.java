package com.kingcheckers.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kingcheckers.game.KingCheckers;

/**
 * Created by Maikkeyy on 5-6-2017.
 */
public abstract class AbstractScreen implements Screen {
    protected OrthographicCamera camera;
    protected Stage stage;
    protected SpriteBatch spriteBatch;
    protected KingCheckers game;

    public AbstractScreen() {

    }

    public AbstractScreen(KingCheckers game) {
        this.game = game;
        createCamera();

        stage = new Stage();
        spriteBatch = new SpriteBatch();
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, KingCheckers.WIDTH, KingCheckers.HEIGHT);
        camera.update();
    }

    protected void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override public void render(float delta) { }
    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        spriteBatch.dispose();
    }
}
