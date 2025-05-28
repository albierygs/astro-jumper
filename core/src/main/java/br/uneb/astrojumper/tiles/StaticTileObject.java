package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

public class StaticTileObject extends TileObject implements ITileObject {
    public StaticTileObject(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        fixture.getFilterData().categoryBits = Constants.GROUND_BIT;
        fixture.getFilterData().maskBits = Constants.PLAYER_BIT | Constants.METEOR_BIT | Constants.RAY_BIT;
    }

    @Override
    public void colide() {
        Gdx.app.log("Static colide", "");
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Batch batch) {

    }

    @Override
    public void dispose() {

    }
}
