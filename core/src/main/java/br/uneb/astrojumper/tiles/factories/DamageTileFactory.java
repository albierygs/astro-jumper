package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.Damage;
import br.uneb.astrojumper.tiles.ITileObject;
import com.badlogic.gdx.maps.MapObject;

public class DamageTileFactory implements TileFactory {
    @Override
    public ITileObject createTile(PlayScreen playScreen, MapObject object) {
        return new Damage(playScreen, object);
    }
}
