package br.uneb.astrojumper.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontManager {

    private static BitmapFont font;

    public static void load() {
        if (font == null) {
            font = new BitmapFont(Gdx.files.internal("fonts/orbitron.fnt"));
        }
    }

    public static BitmapFont getFont() {
        return font;
    }

    public static void dispose() {
        if (font != null) {
            font.dispose();
        }
    }
}
