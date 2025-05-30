package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

// classe abstrata para todos os tiles do mapa
public abstract class TileObject {
    protected PlayScreen playScreen;
    protected MapObject bounds;
    protected Body body;
    protected Fixture fixture;

    public TileObject(PlayScreen playScreen, MapObject bounds) {
        this.playScreen = playScreen;

        World world = playScreen.getWorld();
        TiledMap map = playScreen.getMap();
        this.bounds = bounds;

        // configurações do body do tile
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // definindo os filtros de colisão do tile
        fixtureDef.filter.categoryBits = Constants.GROUND_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.METEOR_BIT | Constants.RAY_BIT;

        // configurações da forma do tile
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

        if (fixture != null) {
            fixture.setUserData(this);
        }
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
}

