package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class TileObject {
    protected Fixture fixture;
    protected Map map;
    protected Body body;

    public TileObject(World world, TiledMap map, MapObject bounds) {
        this.map = map;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        if (bounds instanceof RectangleMapObject) {
            Rectangle rectangle = ((RectangleMapObject) bounds).getRectangle();
            bodyDef.position.set(
                (rectangle.getX() + rectangle.getWidth() / 2) / Constants.PIXELS_PER_METER,
                (rectangle.getY() + rectangle.getHeight() / 2) / Constants.PIXELS_PER_METER
            );

            body = world.createBody(bodyDef);

            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(
                rectangle.getWidth() / 2 / Constants.PIXELS_PER_METER,
                rectangle.getHeight() / 2 / Constants.PIXELS_PER_METER
            );

            fixtureDef.shape = polygonShape;
            fixture = body.createFixture(fixtureDef);

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
                    vertices[i * 2] / Constants.PIXELS_PER_METER,
                    vertices[i * 2 + 1] / Constants.PIXELS_PER_METER
                );
            }

            polygonShape.set(worldVertices);
            fixtureDef.shape = polygonShape;
            fixture = body.createFixture(fixtureDef);

            polygonShape.dispose();
        }
    }

    public abstract void colide();
}

