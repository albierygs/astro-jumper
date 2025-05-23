package br.uneb.astrojumper.tiles;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.World;

public class WorldObjectsManager {
    public WorldObjectsManager(World world, TiledMap map) {
        // create ground, damage, collision, final spaceship and stars layers
        for (MapLayer layer : map.getLayers()) {
            if (!(layer instanceof TiledMapTileLayer) && layer.getObjects().getCount() > 0 && !layer.getName().equalsIgnoreCase("meteor-spawns")) {
                for (MapObject object : layer.getObjects()) {
                    if (layer.getName().equalsIgnoreCase("damage")
                        || layer.getName().equalsIgnoreCase("rays")
                        || layer.getName().equalsIgnoreCase("final")
                    ) {
                        if (layer.getName().equalsIgnoreCase("damage")) {
                            new Damage(world, map, object);
                        } else if (layer.getName().equalsIgnoreCase("rays")) {
                            new Ray(world, map, object);
                        } else if (layer.getName().equalsIgnoreCase("final")) {
                            new FinalSpaceship(world, map, object);
                        }
                    } else {
                        new StaticTileObject(world, map, object);
                    }
                }
            }
        }
    }
}
