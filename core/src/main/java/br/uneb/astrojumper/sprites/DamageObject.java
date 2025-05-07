package br.uneb.astrojumper.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class DamageObject extends InteractiveTileObject {
    public DamageObject(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
