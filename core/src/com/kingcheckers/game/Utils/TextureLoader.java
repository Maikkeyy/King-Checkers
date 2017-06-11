package com.kingcheckers.game.Utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Maikkeyy on 11-6-2017.
 */
public class TextureLoader {

    /* Method for creating a Drawable object for a shape filled with a color */
    public static Drawable getDrawable(int width, int height, Color col) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(col);
        pixmap.fill();
        Drawable drw = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        return drw;
    }
}
