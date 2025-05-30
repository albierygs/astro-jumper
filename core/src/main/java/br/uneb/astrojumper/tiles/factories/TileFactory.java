package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.ITileObject;
import com.badlogic.gdx.maps.MapObject;

public interface TileFactory {
    ITileObject createTile(PlayScreen playScreen, MapObject object);
}
