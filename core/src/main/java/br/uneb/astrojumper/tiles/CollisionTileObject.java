package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;

public class CollisionTileObject extends TileObject implements ITileObject {
    public CollisionTileObject(PlayScreen playScreen, MapObject bounds) {
        super(playScreen, bounds);
        fixture.getFilterData().categoryBits = bounds.getProperties().get("enemy") != null ? Constants.COLLISION_ENEMY_BIT : Constants.COLLISION_BIT;
        fixture.getFilterData().maskBits = Constants.PLAYER_BIT | Constants.METEOR_BIT | Constants.RAY_BIT | Constants.ENEMY_BIT | Constants.PROJECTILE_BIT;
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
