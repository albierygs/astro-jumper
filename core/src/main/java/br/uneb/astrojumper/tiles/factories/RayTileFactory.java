package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.entities.Ray;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.ITileObject;
import com.badlogic.gdx.maps.MapObject;

public class RayTileFactory implements TileFactory {
    @Override
    public ITileObject createTile(PlayScreen playScreen, MapObject object) {
        return new Ray(playScreen, object);
    }
}
