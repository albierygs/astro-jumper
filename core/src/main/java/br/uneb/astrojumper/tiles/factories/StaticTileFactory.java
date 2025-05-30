package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.ITileObject;
import br.uneb.astrojumper.tiles.StaticTileObject;
import com.badlogic.gdx.maps.MapObject;

public class StaticTileFactory implements TileFactory {

    @Override
    public ITileObject createTile(PlayScreen playScreen, MapObject object) {
        return new StaticTileObject(playScreen, object);
    }
}
