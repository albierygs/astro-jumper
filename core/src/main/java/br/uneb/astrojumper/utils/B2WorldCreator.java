package br.uneb.astrojumper.utils;

import br.uneb.astrojumper.sprites.*;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        // create ground bodies/fixtures
        for (MapObject object : map.getLayers().get(3).getObjects()) {
            new Ground(world, map, object);
        }

        // create damage bodies/fixtures
        for (MapObject object : map.getLayers().get(4).getObjects()) {
            new DamageObject(world, map, object);
        }

        // create collision bodies/fixtures
        for (MapObject object : map.getLayers().get(6).getObjects()) {
            new CollisionObject(world, map, object);
        }

        // create final spaceship
        new FinalSpaceship(world, map, map.getLayers().get(7).getObjects().get(0));

        // create stars bodies/fixtures
        for (MapObject object : map.getLayers().get(5).getObjects()) {
            new Star(world, map, object);
        }
    }
}
