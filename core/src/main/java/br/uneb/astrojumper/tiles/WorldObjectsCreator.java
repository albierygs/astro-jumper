package br.uneb.astrojumper.tiles;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class WorldObjectsCreator {
    public WorldObjectsCreator(World world, TiledMap map) {
        for (MapObject object : map.getLayers().get(3).getObjects()) {
            new InteractiveTileObject(world, map, object);
        }

        // create damage bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects()) {
            new InteractiveTileObject(world, map, object);
        }

        // create collision bodies/fixtures
        for (MapObject object : map.getLayers().get(6).getObjects()) {
            new InteractiveTileObject(world, map, object);
        }

        // create final spaceship
        new InteractiveTileObject(world, map, map.getLayers().get(7).getObjects().get(0));

        // create stars bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects()) {
            new InteractiveTileObject(world, map, object);
        }
    }
}
