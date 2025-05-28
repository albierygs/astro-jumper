package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.tiles.ITileObject;
import br.uneb.astrojumper.tiles.StaticTileObject;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class StaticTileFactory implements TileFactory {

    @Override
    public ITileObject createTile(World world, TiledMap map, MapObject object) {
        return new StaticTileObject(world, map, object);
    }
}
