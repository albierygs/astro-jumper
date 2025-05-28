package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Damage extends TileObject implements ITileObject {

    public Damage(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
        body.setType(BodyDef.BodyType.StaticBody);

        fixture.getFilterData().categoryBits = Constants.DAMAGE_BIT;
        fixture.getFilterData().maskBits = Constants.PLAYER_BIT;
    }

    @Override
    public void colide() {
        System.out.println("Colisão damage");
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
