package br.uneb.astrojumper.entities;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Astronaut extends Sprite {
    private PlayScreen playScreen;
    private Body body;
    private int remainingLifes;
    private boolean takingDamage;

    public Astronaut(PlayScreen playScreen) {
        this.playScreen = playScreen;
        remainingLifes = 3;
        takingDamage = false;
        defineAstronaut();
    }

    private void defineAstronaut() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(500 / Constants.PIXELS_PER_METER, 100 / Constants.PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = playScreen.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / Constants.PIXELS_PER_METER);
        fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
        fixtureDef.filter.maskBits = Constants.GROUND_BIT | Constants.RAY_BIT | Constants.METEOR_BIT | Constants.DAMAGE_BIT | Constants.FINAL_SPACESHIP_BIT;

        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            this.getBody().applyLinearImpulse(new Vector2(0, 4f), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && this.getBody().getLinearVelocity().x <= 2) {
            this.getBody().applyLinearImpulse(new Vector2(1.5f, 0), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.getBody().getLinearVelocity().x >= -2) {
            this.getBody().applyLinearImpulse(new Vector2(-0.1f, 0), this.getBody().getWorldCenter(), true);
        }
    }

    public void receiveDamage(float upwardImpulse, float horizontalImpulse) {
        if (!takingDamage) {
            remainingLifes--;
            takingDamage = true;
            this.body.applyLinearImpulse(new Vector2(horizontalImpulse, upwardImpulse), this.body.getWorldCenter(), true);

            if (isDead()) {
                playScreen.setGameOver(true);
            }
        }
    }

    public void resetDamageState() {
        this.takingDamage = false;
    }

    public boolean isDead() {
        return remainingLifes <= 0;
    }

    public Body getBody() {
        return body;
    }
}
