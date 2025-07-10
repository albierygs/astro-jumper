package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.GroundTileObject;
import br.uneb.astrojumper.tiles.ITileObject;
import com.badlogic.gdx.maps.MapObject;

public class GroundTileFactory implements TileFactory {

    @Override
    public ITileObject createTile(PlayScreen playScreen, MapObject object) {
        return new GroundTileObject(playScreen, object);
    }
}
