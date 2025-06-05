package br.uneb.astrojumper;

import br.uneb.astrojumper.screens.MainMenuScreen;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMap;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */


public class AstroJumper extends Game {
    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new PlayScreen(this, AssetLoader.get("level1.tmx", TiledMap.class)));
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
