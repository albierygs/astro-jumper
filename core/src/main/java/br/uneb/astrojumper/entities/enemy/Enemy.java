package br.uneb.astrojumper.entities.enemy;

import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Enemy extends Sprite {
    protected EnemyType type;
    protected Body body;
    protected Fixture fixture;

    protected boolean defeated;
    protected float deathAnimationTime;

    public Enemy(EnemyType type, int radius) {
        this.type = type;
        this.defeated = false;
        this.deathAnimationTime = 0f;

        defineEnemy(radius);
    }

    protected void defineEnemy(float radius) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = type.getPlayScreen().getWorld().createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Constants.PIXELS_PER_METER);
        fixtureDef.filter.categoryBits = Constants.ENEMY_BIT;
        fixtureDef.filter.maskBits =
            Constants.GROUND_BIT | Constants.DAMAGE_BIT | Constants.FINAL_SPACESHIP_BIT | Constants.PLAYER_BIT | Constants.ENEMY_BIT | Constants.COLLISION_BIT | Constants.COLLISION_ENEMY_BIT | Constants.PROJECTILE_BIT;

        fixtureDef.shape = shape;

        fixture = body.createFixture(fixtureDef);
    }

    protected void update(float delta) {
        if (defeated) {
            deathAnimationTime += delta;

            body.setLinearVelocity(0, 0);
        }
    }

    @Override
    public void draw(Batch batch) {
        if (defeated && type.getAnimations().containsKey("death")) {
            Animation<TextureRegion> deathAnim = type.getAnimations().get("death");
            if (!deathAnim.isAnimationFinished(deathAnimationTime)) {
                TextureRegion currentFrame = deathAnim.getKeyFrame(deathAnimationTime, false);
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
        } else if (!defeated) {
            super.draw(batch);
        }
    }

    private void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public void defeat() {
        if (!this.defeated) {
            this.defeated = true;
            this.deathAnimationTime = 0f;
            this.body.setLinearVelocity(0, 0);
            setCategoryFilter(Constants.DESTROYED_BIT);
        }
    }

    public Body getBody() {
        return body;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public boolean isDeathAnimationFinished() {
        if (type.getAnimations().containsKey("death")) {
            return type.getAnimations().get("death").isAnimationFinished(deathAnimationTime);
        }
        return true;
    }
}
