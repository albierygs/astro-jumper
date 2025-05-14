package br.uneb.astrojumper.sprites;

import br.uneb.astrojumper.AstroJumper;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class InteractiveTileObject {
    private World world;
    private TiledMap map;
    private TiledMapTile tile;
    private Body body;
    private MapObject bounds;

    public InteractiveTileObject(World world, TiledMap map, MapObject bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        if (bounds instanceof RectangleMapObject) {
            Rectangle rectangle = ((RectangleMapObject) bounds).getRectangle();
            bodyDef.position.set(
                (rectangle.getX()  + rectangle.getWidth() / 2) / AstroJumper.PIXELS_PER_METER,
                (rectangle.getY() + rectangle.getHeight() / 2) / AstroJumper.PIXELS_PER_METER
            );

            body = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(
                rectangle.getWidth() / 2 / AstroJumper.PIXELS_PER_METER,
                rectangle.getHeight() / 2 / AstroJumper.PIXELS_PER_METER
            );

            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
            polygonShape.dispose();
        } else if (bounds instanceof PolygonMapObject) {
            Polygon polygon = ((PolygonMapObject) bounds).getPolygon();
            float[] vertices = polygon.getTransformedVertices();

            bodyDef.position.set(0, 0);
            body = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            Vector2[] worldVertices = new Vector2[vertices.length / 2];

            for (int i = 0; i < vertices.length / 2; i++) {
                worldVertices[i] = new Vector2(
                    vertices[i * 2] / AstroJumper.PIXELS_PER_METER,
                    vertices[i * 2 + 1] / AstroJumper.PIXELS_PER_METER
                );
            }

            polygonShape.set(worldVertices);
            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);
            polygonShape.dispose();
        }
    }
}
