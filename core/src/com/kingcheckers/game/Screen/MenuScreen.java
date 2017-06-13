package com.kingcheckers.game.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kingcheckers.game.KingCheckers;
import com.kingcheckers.game.Utils.Font;

/**
 * Created by Maikkeyy on 13-6-2017.
 */
public class MenuScreen extends AbstractScreen {
    private int logoWidth = 494;
    private int logoHeight = 223;
    private final int buttonFontSize = 25;
    private int buttonWidth = 350;
    private int buttonHeight = 77;
    private Sprite logo;

    private Table buttonContainer;
    private TextButton startBattleBtn, exitBtn;

    public MenuScreen(KingCheckers game) {
        super(game);

        createLogo();
        createButtons();
        createButtonListeners();
        createButtonContainer();

        stage.addActor(buttonContainer);
    }

    private void createLogo() {
        logo = new Sprite(new Texture("logo.png"));
        logo.setPosition(KingCheckers.WIDTH / 2 - logoWidth / 2, KingCheckers.HEIGHT / 2 - logoHeight / 2 + 100);
    }

    private void createButtonContainer() {
        buttonContainer = new Table();
        buttonContainer.setPosition(KingCheckers.WIDTH/2, KingCheckers.HEIGHT/2 - 80);

        buttonContainer.add(startBattleBtn).size(buttonWidth, buttonHeight);
        buttonContainer.row().padTop(5);
        buttonContainer.add(exitBtn).size(buttonWidth, buttonHeight);
    }

    private void createButtons() {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = Font.get(buttonFontSize);
        style.up = new TextureRegionDrawable(new Sprite(new Texture("button.png")));
       // style.down = TextureLoader.getDrawable("buttonPressed");

        startBattleBtn = new TextButton("Start battle!", style);
        exitBtn = new TextButton("Exit", style);
    }

    private void createButtonListeners() {
        startBattleBtn.addListener(new ChangeListener() {
            @Override public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(KingCheckers.ScreenMode.BATTLE);
                System.out.println("clicked");
            }
        });

        exitBtn.addListener(new ChangeListener() {
            @Override public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        clearScreen();

        spriteBatch.begin();
        logo.draw(spriteBatch);
        spriteBatch.end();
        stage.draw();
    }

    @Override public void dispose() {
        super.dispose();
    }
}
