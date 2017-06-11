package com.kingcheckers.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.kingcheckers.game.KCGame;

/**
 * Created by Maikkeyy on 5-6-2017.
 */
public abstract class AbstractScreen implements Screen {
    protected OrthographicCamera camera;
    protected Stage stage;
    protected SpriteBatch spriteBatch;
    protected KCGame game;

    public AbstractScreen(KCGame game) {
        this.game = game;
        createCamera();

        stage = new Stage();
        spriteBatch = new SpriteBatch();
    }

    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, KCGame.WIDTH, KCGame.HEIGHT);
        camera.update();
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
