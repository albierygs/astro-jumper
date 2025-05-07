package br.uneb.astrojumper.sprites;

import br.uneb.astrojumper.AstroJumper;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Astronaut extends Sprite {
    private World world;
    private Body body;

    public Astronaut(World world) {
        this.world = world;
        defineAstronaut();
    }

    private void defineAstronaut() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100 / AstroJumper.PIXELS_PER_METER, 100 / AstroJumper.PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / AstroJumper.PIXELS_PER_METER);

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
