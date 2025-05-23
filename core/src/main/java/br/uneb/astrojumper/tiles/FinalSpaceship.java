package br.uneb.astrojumper.tiles;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class FinalSpaceship extends InteractiveTileObject {

    public FinalSpaceship(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void colide() {
        System.out.println("Colisão nave");
    }
}
