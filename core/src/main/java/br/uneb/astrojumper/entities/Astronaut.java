package br.uneb.astrojumper.entities;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Astronaut extends Sprite {
    private PlayScreen playScreen;
    private Body body;
    private int remainingLifes;
    private boolean takingDamage;

    private State currentState;

    private State previousState;
    private Animation<TextureRegion> astronautIdle;
    private Animation<TextureRegion> astronautWalk;
    private Animation<TextureRegion> astronautJump;
    private Animation<TextureRegion> astronautFalling;
    private Animation<TextureRegion> astronautDead;
    private float stateTimer;
    private boolean runningRight;

    public Astronaut(PlayScreen playScreen) {
        this.playScreen = playScreen;
        remainingLifes = 3;
        takingDamage = false;

        setBounds(0, 0, 48 / Constants.PIXELS_PER_METER, 48 / Constants.PIXELS_PER_METER);

        defineAstronaut();
        defineAnimations();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
    }

    private void defineAnimations() {
        // animação astronauta parado
        astronautIdle = createAnimation(
            AssetLoader.get("astronaut-idle.png", Texture.class),
            12,
            0,
            0.1f
        );

        // animação astronauta morte
        astronautDead = createAnimation(
            AssetLoader.get("astronaut-dead.png", Texture.class),
            15,
            0,
            0.1f
        );

        // animação astronauta pulando
        astronautJump = createAnimation(
            AssetLoader.get("astronaut-jump.png", Texture.class),
            5,
            0,
            0.5f
        );

        // animação astronauta caindo
        astronautFalling = createAnimation(
            AssetLoader.get("astronaut-jump.png", Texture.class),
            10,
            5,
            0.5f
        );

        // animação astronauta andando
        astronautWalk = createAnimation(
            AssetLoader.get("astronaut-walk.png", Texture.class),
            8,
            0,
            0.1f
        );
    }

    private Animation<TextureRegion> createAnimation(Texture texture, int finalFrame, int startFrame, float duration) {
        int frameWidth = 64;
        int frameHeight = 64;

        TextureRegion[][] tmp = TextureRegion.split(texture, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[finalFrame];

        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = startFrame; j < finalFrame; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(duration, frames);
    }

    private void defineAstronaut() {
        float xBeginPosition =
            ((RectangleMapObject) playScreen.getMap().getLayers().get("begin").getObjects().get(0)).getRectangle().x;
        float yBeginPosition =
            ((RectangleMapObject) playScreen.getMap().getLayers().get("begin").getObjects().get(0)).getRectangle().y;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xBeginPosition / Constants.PIXELS_PER_METER, yBeginPosition / Constants.PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = playScreen.getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / Constants.PIXELS_PER_METER);
        fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
        fixtureDef.filter.maskBits = Constants.GROUND_BIT | Constants.RAY_BIT | Constants.METEOR_BIT | Constants.DAMAGE_BIT | Constants.FINAL_SPACESHIP_BIT;

        fixtureDef.shape = shape;
        fixtureDef.friction = 0.05f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setLinearDamping(3.5f);
    }

    public void update(float deltaTime) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        if (isDead()) {
            playScreen.setGameOver(true);
            body.setLinearVelocity(new Vector2(0, 0));
        } else {
            handleInput(deltaTime);
        }

        setRegion(getFrame(deltaTime));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        if (currentState == previousState) {
            stateTimer += delta;
        } else {
            stateTimer = 0;
        }

        switch (currentState) {
            case JUMPING:
                region = astronautJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = astronautWalk.getKeyFrame(stateTimer, true); // true para loop
                break;
            case FALLING:
                region = astronautFalling.getKeyFrame(stateTimer);
                break;
            case DEAD:
                region = astronautDead.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = astronautIdle.getKeyFrame(stateTimer, true); // true para loop
                break;
        }

        // Vira o sprite se estiver correndo para a esquerda
        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        // Vira o sprite de volta para a direita
        else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        previousState = currentState;
        return region;
    }

    public State getState() {
        if (isDead()) {
            return State.DEAD;
        }
        if (body.getLinearVelocity().y > 0) { // Pulando
            return State.JUMPING;
        }
        if (body.getLinearVelocity().y < 0) { // Caindo
            return State.FALLING;
        }
        if (body.getLinearVelocity().x != 0) { // Correndo
            return State.RUNNING;
        }
        return State.STANDING; // Parado
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && this.getBody().getLinearVelocity().x <= 3) {
            this.getBody().applyLinearImpulse(new Vector2(3.0f - body.getLinearVelocity().x, 0), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.getBody().getLinearVelocity().x >= -3) {
            this.getBody().applyLinearImpulse(new Vector2(-3 - body.getLinearVelocity().x, 0), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && (currentState == State.STANDING || currentState == State.RUNNING)) {
            this.getBody().applyLinearImpulse(new Vector2(0, 7f), this.getBody().getWorldCenter(), true);
        }
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void receiveDamage(float upwardImpulse, float horizontalImpulse) {
        if (!takingDamage) {
            remainingLifes--;
            playScreen.getHud().setLifes(Math.max(remainingLifes, 0));
            takingDamage = true;
            this.body.setLinearVelocity(new Vector2(0, 0));
            this.body.applyLinearImpulse(new Vector2(horizontalImpulse, upwardImpulse), this.body.getWorldCenter(), true);
        }
    }

    public void resetDamageState() {
        this.takingDamage = false;
    }

    public void dispose() {
        if (body != null && playScreen.getWorld() != null) {
            playScreen.getWorld().destroyBody(body);
            body = null;
        }
    }

    public boolean isDeadAnimationFinished() {
        return astronautDead.isAnimationFinished(stateTimer);
    }

    public boolean isDead() {
        if (body.getPosition().y < 0) {
            remainingLifes = 0;
            playScreen.getHud().setLifes(remainingLifes);
        }
        return remainingLifes <= 0;
    }

    public int getRemainingLives() {
        return remainingLifes;
    }

    public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD}

    public Body getBody() {
        return body;
    }
}
