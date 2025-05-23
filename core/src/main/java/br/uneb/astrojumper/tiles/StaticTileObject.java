package br.uneb.astrojumper.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class StaticTileObject extends TileObject {
    public StaticTileObject(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        if (fixture != null) {
            fixture.setUserData(this);
        }
    }

    @Override
    public void colide() {
        Gdx.app.log("Static colide", "");
    }
}
