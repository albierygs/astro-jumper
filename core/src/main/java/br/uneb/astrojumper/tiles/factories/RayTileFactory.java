package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.entities.Ray;
import br.uneb.astrojumper.tiles.ITileObject;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class RayTileFactory implements TileFactory {
    @Override
    public ITileObject createTile(World world, TiledMap map, MapObject object) {
        return new Ray(world, map, object);
    }
}
