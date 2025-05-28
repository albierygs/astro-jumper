package br.uneb.astrojumper.tiles.factories;

import br.uneb.astrojumper.tiles.ITileObject;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public interface TileFactory {
    ITileObject createTile(World world, TiledMap map, MapObject object);
}
