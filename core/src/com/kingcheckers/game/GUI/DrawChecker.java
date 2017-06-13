package com.kingcheckers.game.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.kingcheckers.game.Model.*;

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

    public DrawChecker(Player player, ActiveChecker activeChecker, int checkerType, int x, int y) {
        originalPlayer = player;
        this.activeChecker = activeChecker;
        boardPos = new BoardPosition(x, y);
        whiteRegular = new Texture("white_regular.png");
        whiteKing = new Texture("white_king.png");
        blackRegular = new Texture("black_regular.png");
        blackKing = new Texture("black_king.png");



        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return isTouched();
            }});

    }

    private boolean isTouched() {
        if(originalPlayer.getPlayerSide() == playerSide) {
            System.out.println("Pressed pawn on position: " + boardPos);

            if(activeChecker.getDrawChecker() == this) {
                activeChecker.unselect();
            }
            else {
                activeChecker.select(this);
            }
        }
        return false;
    }

    public BoardPosition getBoardPosition() {
        return boardPos;
    }

    public PawnType getType() {
        return this.type;
    }

    public PlayerSide getPlayerSide() {
        return this.playerSide;
    }
}
