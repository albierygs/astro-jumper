package br.uneb.astrojumper.entities;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.ITileObject;
import br.uneb.astrojumper.tiles.TileObject;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SpaceshipBullet extends TileObject implements ITileObject {

    private static final float LASER_SPEED = 5f;
    private static final float LASER_WIDTH = 20;
    private static final float LASER_HEIGHT = 10;
    private Animation<TextureRegion> flyingAnimation;
    private Animation<TextureRegion> explosionAnimation;
    private Sound explosionSound;
    private float stateTime;
    private boolean exploding;
    private boolean finished;
    private Vector2 initialDirection;

    public SpaceshipBullet(PlayScreen playScreen, Vector2 initialPixelPosition, Vector2 direction) {
        super(playScreen, new RectangleMapObject(
            initialPixelPosition.x,
            initialPixelPosition.y,
            LASER_WIDTH,
            LASER_HEIGHT
        ));

        this.initialDirection = direction.nor();

        body.setType(BodyDef.BodyType.DynamicBody);
        body.setGravityScale(0);

        body.setTransform(initialPixelPosition.x / Constants.PIXELS_PER_METER,
            initialPixelPosition.y / Constants.PIXELS_PER_METER,
            0
        );

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((LASER_WIDTH / 2f) / Constants.PIXELS_PER_METER,
            (LASER_HEIGHT / 2f) / Constants.PIXELS_PER_METER
        );

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.PROJECTILE_BIT;
        fdef.filter.maskBits =
            Constants.PLAYER_BIT | Constants.GROUND_BIT | Constants.COLLISION_BIT | Constants.DAMAGE_BIT | Constants.METEOR_BIT | Constants.RAY_BIT;

        fixture = body.createFixture(fdef);
        fixture.setUserData(this);
        shape.dispose();

        flyingAnimation = createAnimation(AssetLoader.get("spaceship-bullet.png", Texture.class), 4, 0, 0.1f); //
        explosionAnimation = createAnimation(AssetLoader.get("spaceship-bullet-explosion.png", Texture.class), 10, 0,
            0.05f);
        explosionSound = AssetLoader.get("meteor-impact.mp3", Sound.class);

        stateTime = 0;
        exploding = false;
        finished = false;

        body.setLinearVelocity(initialDirection.x * LASER_SPEED, initialDirection.y * LASER_SPEED);
    }

    private Animation<TextureRegion> createAnimation(Texture texture, int numFrames, int startFrame, float frameDuration) {
        int frameWidth = texture.getWidth() / numFrames;
        int frameHeight = texture.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(texture, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[numFrames];

        int index = 0;
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                if (index < numFrames) {
                    frames[index++] = tmp[i][j];
                }
            }
        }

        return new Animation<TextureRegion>(frameDuration, frames);
    }

    @Override
    public void update(float delta) {
        if (finished) {
            return;
        }

        if (exploding) {
            stateTime += delta;
            if (explosionAnimation.isAnimationFinished(stateTime)) {
                finished = true;
            }
            body.setLinearVelocity(0, 0);
        } else {
            stateTime += delta;
        }
    }

    @Override
    public void render(Batch batch) {
        if (finished) {
            return;
        }

        TextureRegion currentFrame;
        if (exploding) {
            currentFrame = explosionAnimation.getKeyFrame(stateTime);
        } else {
            currentFrame = flyingAnimation.getKeyFrame(stateTime, true);
        }

        if (currentFrame == null) {
            currentFrame = flyingAnimation.getKeyFrames()[0];
        }

        float width = currentFrame.getRegionWidth() / Constants.PIXELS_PER_METER;
        float height = currentFrame.getRegionHeight() / Constants.PIXELS_PER_METER;

        batch.draw(currentFrame,
            body.getPosition().x - width / 2f,
            body.getPosition().y - height / 2f,
            width,
            height
        );
    }

    @Override
    public void colide() {
        if (!exploding) {
            exploding = true;
            stateTime = 0;
            explosionSound.play(0.5f);
            body.setLinearVelocity(0, 0);
            setCategoryFilter(Constants.DESTROYED_BIT);
        }
    }

    @Override
    public void dispose() {
        if (body != null && playScreen.getWorld() != null) {
            playScreen.getWorld().destroyBody(body);
            body = null;
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
