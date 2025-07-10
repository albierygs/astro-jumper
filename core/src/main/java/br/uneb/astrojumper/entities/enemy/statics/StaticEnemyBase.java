package br.uneb.astrojumper.entities.enemy.statics;

import br.uneb.astrojumper.entities.enemy.Enemy;
import br.uneb.astrojumper.entities.enemy.EnemyType;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;

public abstract class StaticEnemyBase extends Enemy implements StaticEnemy {
    protected float stateTime;

    protected PlayScreen playScreen;

    public StaticEnemyBase(EnemyType type, int radius, PlayScreen playScreen) {
        super(type, radius);
        this.stateTime = 0;
        this.playScreen = playScreen;

        body.setType(BodyDef.BodyType.StaticBody);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (defeated) {
            return;
        }

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch) {
        TextureRegion currentFrame;
        if (defeated && type.getAnimations().containsKey("death")) {
            super.draw(batch);
            return;
        } else if (type.getAnimations().containsKey("idle")) {
            currentFrame = type.getAnimations().get("idle").getKeyFrame(stateTime, true);
        } else {
            super.draw(batch);
            return;
        }

        if (currentFrame != null) {
            float width = currentFrame.getRegionWidth() / Constants.PIXELS_PER_METER;
            float height = currentFrame.getRegionHeight() / Constants.PIXELS_PER_METER;
            batch.draw(currentFrame,
                body.getPosition().x - width / 2f,
                body.getPosition().y - height / 2f,
                width,
                height
            );
        }
    }
}
