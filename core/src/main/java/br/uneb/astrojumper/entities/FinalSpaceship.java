package br.uneb.astrojumper.entities;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.tiles.ITileObject;
import br.uneb.astrojumper.tiles.TileObject;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class FinalSpaceship extends TileObject implements ITileObject {

    private Animation<TextureRegion> animation;
    private float animationTime;
    private boolean animationStarted;
    private boolean animationFinished;
    private Vector2 position;

    public FinalSpaceship(PlayScreen playScreen, MapObject bounds) {
        super(playScreen, bounds);
        body.setType(BodyDef.BodyType.StaticBody);

        fixture.getFilterData().categoryBits = Constants.FINAL_SPACESHIP_BIT;
        fixture.getFilterData().maskBits = Constants.PLAYER_BIT;

        Texture texture = AssetLoader.get("spaceship.png", Texture.class);

        this.animation = defineAnimation(texture);

        Rectangle rect = ((RectangleMapObject) bounds).getRectangle();

        position = new Vector2(
            (rect.getX() + rect.getWidth() / 2),
            (rect.getY() + rect.getHeight() / 2)
        );

        this.animationTime = 0;
        this.animationStarted = false;
        this.animationFinished = false;
    }

    private Animation<TextureRegion> defineAnimation(Texture texture) {
        int frameWidth = 312;
        int frameHeight = 512;

        TextureRegion[][] tmp = TextureRegion.split(texture, frameWidth, frameHeight);

        TextureRegion[] frames = new TextureRegion[19];

        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 19; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(0.2f, frames);
    }

    // termina a fase
    @Override
    public void colide() {
        if (!animationStarted) {
            setCategoryFilter(Constants.DESTROYED_BIT);
            animationStarted = true;
            animationTime = 0;
        }
    }

    @Override
    public void update(float delta) {
        if (animationStarted && !animationFinished) {
            animationTime += delta;
            if (animation.isAnimationFinished(animationTime)) {
                animationFinished = true;
                playScreen.setCompleted(true);
            }
        }
    }

    @Override
    public void render(Batch batch) {
        if (!animationStarted) {
            TextureRegion currentFrame = animation.getKeyFrames()[0];
            float width = currentFrame.getRegionWidth() / Constants.PIXELS_PER_METER;
            float height = currentFrame.getRegionHeight() / Constants.PIXELS_PER_METER;
            batch.draw(currentFrame,
                position.x / Constants.PIXELS_PER_METER - width / 2f,
                position.y / Constants.PIXELS_PER_METER - height / 2f, width, height
            );
        } else if (animationStarted && !animationFinished) {
            TextureRegion currentFrame = animation.getKeyFrame(animationTime, false);
            float width = currentFrame.getRegionWidth() / Constants.PIXELS_PER_METER;
            float height = currentFrame.getRegionHeight() / Constants.PIXELS_PER_METER;
            batch.draw(currentFrame, position.x / Constants.PIXELS_PER_METER - width / 2f, position.y / Constants.PIXELS_PER_METER - height / 2f, width, height);
        }
    }

    @Override
    public void dispose() {

    }

    public boolean isAnimationStarted() {
        return animationStarted;
    }

    public boolean isAnimationFinished() {
        return animationFinished;
    }
}
