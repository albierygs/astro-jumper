package br.uneb.astrojumper.entities.enemy.movable;

import br.uneb.astrojumper.entities.enemy.Enemy;
import br.uneb.astrojumper.entities.enemy.EnemyType;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class MovableEnemyBase extends Enemy implements MovableEnemy {
    protected float walkSpeed;
    protected boolean movingRight;
    protected float stateTime;

    public MovableEnemyBase(EnemyType type, int radius) {
        super(type, radius);
        this.walkSpeed = 1.5f;
        this.movingRight = true;
        this.stateTime = 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (!defeated) {
            stateTime += delta;
            setRegion(getFrame(delta));

            float currentHorizontalSpeed = movingRight ? walkSpeed : -walkSpeed;
            body.setLinearVelocity(currentHorizontalSpeed, body.getLinearVelocity().y);

            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
    }

    @Override
    public void turn() {
        movingRight = !movingRight;
    }

    protected TextureRegion getFrame(float delta) {
        TextureRegion region = type.getAnimations().get("walk").getKeyFrame(stateTime, true);
        if (!movingRight && !region.isFlipX()) {
            region.flip(true, false);
        } else if (movingRight && region.isFlipX()) {
            region.flip(true, false);
        }
        return region;
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
