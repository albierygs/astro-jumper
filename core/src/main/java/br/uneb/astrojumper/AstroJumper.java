package br.uneb.astrojumper;

import br.uneb.astrojumper.screens.PlayScreen;
import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AstroJumper extends Game {
    public static final int VIRTUAL_WIDTH = 1100;
    public static final int VIRTUAL_HEIGHT = 680;
    public static final float PIXELS_PER_METER = 100;

    @Override
    public void create() {
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
