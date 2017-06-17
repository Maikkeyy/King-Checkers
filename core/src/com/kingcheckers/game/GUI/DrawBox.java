package com.kingcheckers.game.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kingcheckers.game.Model.ActiveChecker;
import com.kingcheckers.game.Model.BoardPosition;
import com.kingcheckers.game.Model.CellType;
import com.kingcheckers.game.Model.Player;
import com.kingcheckers.game.RMI.Client;

import java.io.Serializable;

/**
 * Created by Maikkeyy on 5-6-2017.
 */
public class DrawBox extends Image {
    private BoardPosition boardPos;
    private Texture beigeBox;
    private Texture brownBox;
    private Player player;

    // RMI properties
    private Client client;

    private ActiveChecker activeChecker;

    public DrawBox(Player player, ActiveChecker activeChecker, Client client, int boxType, int x, int y) {
        boardPos = new BoardPosition(x, y);
        this.player = player;
        this.activeChecker = activeChecker;
        this.client = client;
        beigeBox = new Texture("beigeBox.png");
        brownBox = new Texture("brownBox.png");

        if(boxType == 0) {
            this.setDrawable(new TextureRegionDrawable(new Sprite(beigeBox)));
        }
        else {
            this.setDrawable(new TextureRegionDrawable(new Sprite(brownBox)));

            // Brown boxes can be clicked for moving pieces
            this.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return isTouched();
                }
            });
        }

    }

    private boolean isTouched() {
        if(activeChecker.isSelected()) {
            if(activeChecker.canMoveTo(this)) {

                // RMI
                client.broadcastMovePiece("move", getPosition(), boardPos);
            }
            else if(activeChecker.canCapturePawn(boardPos)) {
                // RMI
                client.broadcastCapturePiece("capture", getPosition(), boardPos);
            }
        }
        return false;
    }

    public BoardPosition getBoardPosition() { return boardPos; }

    /* Returns the coords of the base class one step higher in the hierarchy,
       in this case Image, which means that the real drawed coords will be returned
    */
    public Vector2 getPosition() {
        return this.localToParentCoordinates(new Vector2(0,0));
    }
}
