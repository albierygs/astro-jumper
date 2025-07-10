package br.uneb.astrojumper.entities.enemy.statics;

import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.entities.SpaceshipBullet;
import br.uneb.astrojumper.entities.enemy.factories.EnemyFlyweightFactory;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;

import java.util.HashMap;

public class StaticFlyingEnemy extends StaticEnemyBase {
    protected float originalRadius;
    private boolean attacking;
    private float attackStateTime;
    private float attackRange = 5.0f;
    private float attackCooldown = 2.0f;
    private float currentAttackCooldown = 0.0f;
    private Sound attackSound;

    public StaticFlyingEnemy(PlayScreen playScreen) {
        super(EnemyFlyweightFactory.getEnemyType("static-flying", playScreen), 25, playScreen);
        fixture.setUserData(this);
        defineStaticAnimation();
        setBounds(0, 0, 70 / Constants.PIXELS_PER_METER, 70 / Constants.PIXELS_PER_METER);

        this.attacking = false;
        this.attackStateTime = 0;
        this.attackSound = AssetLoader.get("laser-attack.mp3", Sound.class);

        if (fixture != null && fixture.getShape() instanceof CircleShape) {
            this.originalRadius = ((CircleShape) fixture.getShape()).getRadius();
        } else {
            this.originalRadius = 25 / Constants.PIXELS_PER_METER;
        }
    }

    @Override
    public void defineStaticAnimation() {
        Texture idleTexture = AssetLoader.get("static-flying-enemy.png", Texture.class);

        TextureRegion idleFrames = new TextureRegion(idleTexture);
        Animation<TextureRegion> walkAnimation = new Animation<>(0.1f, idleFrames);

        this.type.setAnimations(new HashMap<String, Animation<TextureRegion>>() {{
            put("idle", walkAnimation);
        }});
    }

    @Override
    public void attack() {
        if (currentAttackCooldown <= 0) {
            attackSound.play(0.5f);

            Vector2 laserSpawnPosition = new Vector2(
                body.getPosition().x * Constants.PIXELS_PER_METER,
                body.getPosition().y * Constants.PIXELS_PER_METER
            );

            Vector2 playerPosition = playScreen.getPlayer().getBody().getPosition();
            Vector2 direction = playerPosition.sub(body.getPosition()).nor();

            SpaceshipBullet laser = new SpaceshipBullet(playScreen, laserSpawnPosition, direction);
            playScreen.addProjectile(laser);

            currentAttackCooldown = attackCooldown;
            attackStateTime = 0;
            attacking = true;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (defeated || playScreen.getPlayer().isDead()) {
            return;
        }

        Astronaut player = playScreen.getPlayer();

        if (currentAttackCooldown > 0) {
            currentAttackCooldown -= delta;
            if (currentAttackCooldown < 0) {
                currentAttackCooldown = 0;
            }
        }

        if (player != null && player.getBody() != null) {
            float distanceToPlayer = this.body.getPosition().dst(player.getBody().getPosition());
            boolean playerInAttackRange = (distanceToPlayer <= attackRange);
            boolean enemyIsReadyToAttack = (currentAttackCooldown == 0);

            if (playerInAttackRange && enemyIsReadyToAttack) {
                attack();
            }
        }
    }

    @Override
    public void draw(Batch batch) {
        TextureRegion currentFrame;
        if (defeated && type.getAnimations().containsKey("death")) {
            super.draw(batch);
            return;
        } else if (attacking && type.getAnimations().containsKey("attack")) {
            currentFrame = type.getAnimations().get("attack").getKeyFrame(attackStateTime, false);
            if (currentFrame == null || type.getAnimations().get("attack").isAnimationFinished(attackStateTime)) {
                attacking = false;
                currentFrame = type.getAnimations().get("idle").getKeyFrame(stateTime, true);
            }
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
