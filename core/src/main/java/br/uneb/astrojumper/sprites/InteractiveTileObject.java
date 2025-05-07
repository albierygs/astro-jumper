package br.uneb.astrojumper.sprites;

import br.uneb.astrojumper.AstroJumper;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public abstract class InteractiveTileObject {
    private World world;
    private TiledMap map;
    private TiledMapTile tile;
    private Body body;
    private Rectangle bounds;

    public InteractiveTileObject(World world, TiledMap map,Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX()  + bounds.getWidth() / 2) / AstroJumper.PIXELS_PER_METER,
            (bounds.getY() + bounds.getHeight() / 2) / AstroJumper.PIXELS_PER_METER);

        body = world.createBody(bodyDef);

        polygonShape.setAsBox(bounds.getWidth() / 2 / AstroJumper.PIXELS_PER_METER,
            bounds.getHeight() / 2 / AstroJumper.PIXELS_PER_METER);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);
    }
}
