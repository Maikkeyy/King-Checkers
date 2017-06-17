package com.kingcheckers.game.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.kingcheckers.game.Utils.Font;
import com.kingcheckers.game.Utils.TextureLoader;

import java.io.Serializable;

/**
 * Created by Maikkeyy on 11-6-2017.
 */
public class PlayerInfo extends Table {
    private final float tableWidth = 120;
    private final float tableHeight = 100;
    private final int counterFontSize = 15;
    private final int checkerSize = 30;

    private int playerFontSize = 25;
    private Label.LabelStyle stylePlayer, styleCounter;
    private Label playerName, countPawns, countKings;
    private Image pawnStandard, pawnKing;

    private Drawable standard = new TextureRegionDrawable(new TextureRegion(new Texture("white_regular.png")));
    private Drawable king = new TextureRegionDrawable(new TextureRegion(new Texture("white_king.png")));

    public PlayerInfo(String playerName, String checkerTextureName) {
        this.setSize(tableWidth, tableHeight);

        stylePlayer = new Label.LabelStyle();
        styleCounter = new Label.LabelStyle();
        styleCounter.font = Font.get(counterFontSize);
        styleCounter.fontColor = Color.WHITE;

        this.setBackground(TextureLoader.getDrawable((int)tableWidth, (int)tableHeight, Color.DARK_GRAY));

        // name and counter elements
        addPlayerName(playerName);
        addPawnStandardCounter(checkerTextureName);
        addPawnKingCounter(checkerTextureName);
    }

    public void setValue(int pawns, int kings) {
        countPawns.setText(Integer.toString(pawns));
        countKings.setText(Integer.toString(kings));
    }

    private void addPlayerName(String name) {
        stylePlayer.font = Font.get(playerFontSize);
        stylePlayer.fontColor = Color.WHITE;
        playerName = new Label(name, stylePlayer);

        if(playerName.getWidth() <= tableWidth) {
            // expand playerName in x-dir and y-dir and it spans over 2 cols on a new row
            this.add(playerName).expand().colspan(2).row();
        } // too big font for table
        else {
            playerFontSize -= 3;
            addPlayerName(name);
        }
    }

    private void addPawnStandardCounter(String pawnTextureName) {
        pawnStandard = new Image(standard);
        this.add(pawnStandard).expandX().align(Align.center).size(checkerSize);

        countPawns = new Label("[VALUE]", styleCounter);
        this.add(countPawns).expandX().align(Align.center).row();
    }

    private void addPawnKingCounter(String pawnTextureName) {
        pawnKing = new Image(king);
        this.add(pawnKing).expandX().align(Align.center).size(checkerSize);

        countKings = new Label("[VALUE]", styleCounter);
        this.add(countKings).expandX().align(Align.center);
    }

    public String getName() {
        return playerName.getText().toString();
    }
}
