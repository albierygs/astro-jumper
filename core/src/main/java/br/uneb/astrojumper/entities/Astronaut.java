package br.uneb.astrojumper.entities;

import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Astronaut extends Sprite {
    private final World world;
    private Body body;

    public Astronaut(World world) {
        this.world = world;
        defineAstronaut();
    }

    private void defineAstronaut() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100 / Constants.PIXELS_PER_METER, 100 / Constants.PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Constants.PIXELS_PER_METER);

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    public World getWorld() {
        return world;
    }

    public Body getBody() {
        return body;
    }
}
