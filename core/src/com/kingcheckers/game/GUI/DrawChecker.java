package com.kingcheckers.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kingcheckers.game.Model.*;
import com.kingcheckers.game.RMI.Client;

import java.io.Serializable;

/**
 * Created by Maikkeyy on 5-6-2017.
 */
public class DrawChecker extends Image {
    public enum PawnType {STANDARD, KING}

    private BoardPosition boardPos;
    private Texture whiteRegular;
    private Texture whiteKing;
    private Texture blackRegular;
    private Texture blackKing;

    private PawnType type;
    private Player originalPlayer;
    private PlayerSide playerSide;
    private ActiveChecker activeChecker;

    // RMI properties
    private Client client;

    public DrawChecker(Player player, ActiveChecker activeChecker, Client client, int checkerType, int x, int y) {
        originalPlayer = player;
        this.activeChecker = activeChecker;
        this.client = client;
        boardPos = new BoardPosition(x, y);
        whiteRegular = new Texture("white_regular.png");
        whiteKing = new Texture("white_king.png");
        blackRegular = new Texture("black_regular.png");
        blackKing = new Texture("black_king.png");

        // 2 is beige and 3 is brown
        if(checkerType == 2 || checkerType == 4) {
            this.playerSide = PlayerSide.BEIGE;

            if(checkerType == 2) {
                this.setDrawable(new TextureRegionDrawable(new Sprite(whiteRegular)));
                type = PawnType.STANDARD;
            }
           // else setAsKing();
        }
        else if(checkerType == 3 || checkerType == 5) {
            this.playerSide = PlayerSide.BROWN;

            if(checkerType == 3) {
                this.setDrawable(new TextureRegionDrawable(new Sprite(blackRegular)));
                type = PawnType.STANDARD;
            }
           // else setAsKing();
        }

        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return isTouched();
            }});

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(this.getActions().size > 0) {
            this.act(Gdx.graphics.getDeltaTime());
        }
    }

    private boolean isTouched() {
        if(originalPlayer.getPlayerSide() == playerSide) {
            if(activeChecker.getDrawChecker() == this) {
                client.broadcastSelectChecker("select", getBoardPosition());
            }
            else {
                client.broadcastSelectChecker("select", getBoardPosition());
            }


        }
        return false;
    }

    public void setAsKing() {
        type = PawnType.KING;
        if(playerSide == PlayerSide.BEIGE) {
           // this.setDrawable(TextureLoader.getDrawable("pawnBrightKing")); TODO
        }
       // else this.setDrawable(TextureLoader.getDrawable("pawnDarkKing")); TODO
    }

    public void setBoardPosition(BoardPosition pos) { setBoardPosition(pos.x, pos.y); }
    public void setBoardPosition(int x, int y) { boardPos.setPosition(x, y); }

    public BoardPosition getBoardPosition() {
        return boardPos;
    }

    public PawnType getType() {
        return this.type;
    }

    public PlayerSide getPlayerSide() {
        return this.playerSide;
    }

    public int getPlayerInt() {
        if(playerSide == PlayerSide.BEIGE) {
            return 2;
        }
        else {
            return 3;
        }
    }
}
