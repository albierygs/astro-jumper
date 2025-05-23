package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class Ray extends InteractiveTileObject {

    public Ray(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Constants.STAR_BIT);
    }

    @Override
    public void colide() {
        System.out.println("Colisão ray");
        setCategoryFilter(Constants.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
