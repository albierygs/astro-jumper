package br.uneb.astrojumper.entities.enemy.statics;

import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.entities.enemy.factories.EnemyFlyweightFactory;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.HashMap;

public class StaticGroundEnemy extends StaticEnemyBase {
    protected float attackRadiusMultiplier = 1.5f;
    protected float originalRadius;
    private boolean attacking;
    private float attackStateTime;
    private float attackRange = 1.5f;
    private float attackCooldown = 5.5f;
    private float currentAttackCooldown = 0.0f;
    private Sound attackSound;
    private Fixture attackFixture;

    public StaticGroundEnemy(PlayScreen playScreen) {
        super(EnemyFlyweightFactory.getEnemyType("static-ground", playScreen), 38, playScreen);
        fixture.setUserData(this);
        defineStaticAnimation();
        setBounds(0, 0, 44 / Constants.PIXELS_PER_METER, 83 / Constants.PIXELS_PER_METER);

        this.attacking = false;
        this.attackStateTime = 0;
        this.attackSound = AssetLoader.get("laser-attack.mp3", Sound.class);

        if (fixture != null && fixture.getShape() instanceof CircleShape) {
            this.originalRadius = ((CircleShape) fixture.getShape()).getRadius();
        } else {
            this.originalRadius = 38 / Constants.PIXELS_PER_METER;
        }
    }

    @Override
    public void defineStaticAnimation() {
        TextureRegion idleFrame1 = new TextureRegion(AssetLoader.get("static-ground-enemy-idle1.png", Texture.class));
        TextureRegion idleFrame2 = new TextureRegion(AssetLoader.get("static-ground-enemy-idle2.png", Texture.class));
        TextureRegion idleFrame3 = new TextureRegion(AssetLoader.get("static-ground-enemy-idle3.png", Texture.class));

        TextureRegion[] idleFrames = new TextureRegion[]{idleFrame1, idleFrame2, idleFrame3};

        Animation<TextureRegion> walkAnimation = new Animation<>(0.3f, idleFrames);

        TextureRegion deathFrame1 = new TextureRegion(AssetLoader.get("static-ground-enemy-death1.png", Texture.class));
        TextureRegion deathFrame2 = new TextureRegion(AssetLoader.get("static-ground-enemy-death2.png", Texture.class));
        TextureRegion deathFrame3 = new TextureRegion(AssetLoader.get("static-ground-enemy-death3.png", Texture.class));
        TextureRegion deathFrame4 = new TextureRegion(AssetLoader.get("static-ground-enemy-death4.png", Texture.class));
        TextureRegion deathFrame5 = new TextureRegion(AssetLoader.get("static-ground-enemy-death5.png", Texture.class));

        TextureRegion[] deathFrames = new TextureRegion[]{deathFrame1, deathFrame2, deathFrame3, deathFrame4,
            deathFrame5};

        Animation<TextureRegion> deathAnimation = new Animation<>(0.1f, deathFrames);

        TextureRegion attackFrame1 = new TextureRegion(AssetLoader.get("static-ground-enemy-attack1.png",
            Texture.class));
        TextureRegion attackFrame2 = new TextureRegion(AssetLoader.get("static-ground-enemy-attack2.png",
            Texture.class));
        TextureRegion attackFrame3 = new TextureRegion(AssetLoader.get("static-ground-enemy-attack3.png",
            Texture.class));
        TextureRegion attackFrame4 = new TextureRegion(AssetLoader.get("static-ground-enemy-attack4.png",
            Texture.class));

        TextureRegion[] attackFrames = new TextureRegion[]{attackFrame1, attackFrame2, attackFrame3, attackFrame4};

        Animation<TextureRegion> attackAnimation = new Animation<>(0.1f, attackFrames);

        this.type.setAnimations(new HashMap<String, Animation<TextureRegion>>() {{
            put("idle", walkAnimation);
            put("death", deathAnimation);
            put("attack", attackAnimation);
        }});
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (defeated || playScreen.getPlayer().isDead()) {
            if (attacking) {
                stopAttack();
            } else {
                destroyAttackFixture();
            }
            return;
        }

        Astronaut player = playScreen.getPlayer();

        if (currentAttackCooldown > 0) {
            currentAttackCooldown -= delta;
            if (currentAttackCooldown < 0) {
                currentAttackCooldown = 0;
            }
        }

        if (attacking) {
            attackStateTime += delta;
        }


        if (player != null && player.getBody() != null) {
            float distanceToPlayer = this.body.getPosition().dst(player.getBody().getPosition());
            boolean playerInAttackRange = (distanceToPlayer <= attackRange);
            boolean enemyIsReadyToAttack = (currentAttackCooldown == 0);

            if (playerInAttackRange && enemyIsReadyToAttack) {
                if (!attacking) {
                    attack();
                }
            } else if (!playerInAttackRange) {
                if (attacking) {
                    stopAttack();
                }
            }
        } else {
            if (attacking) {
                stopAttack();
            }
        }
    }

    @Override
    public void attack() {
        if (!attacking && currentAttackCooldown == 0) {
            attacking = true;
            attackStateTime = 0;
            currentAttackCooldown = attackCooldown;

            if (attackSound != null) {
                attackSound.loop();
            }
            createAttackFixture();
        }
    }

    public void stopAttack() {
        if (attacking) {
            attacking = false;
            if (attackSound != null) {
                attackSound.stop();
            }
            destroyAttackFixture();
        }
    }

    private void createAttackFixture() {
        if (attackFixture == null) {
            CircleShape attackCircle = new CircleShape();
            attackCircle.setRadius(originalRadius * attackRadiusMultiplier);

            FixtureDef attackFdef = new FixtureDef();
            attackFdef.shape = attackCircle;
            attackFdef.isSensor = true;
            attackFdef.filter.categoryBits = Constants.ENEMY_BIT;
            attackFdef.filter.maskBits = Constants.PLAYER_BIT;

            attackFixture = body.createFixture(attackFdef);
            attackFixture.setUserData(this);
            attackCircle.dispose();
        }
    }

    private void destroyAttackFixture() {
        if (attackFixture != null && body != null && body.getWorld() != null) {
            body.destroyFixture(attackFixture);
            attackFixture = null;
        } else if (attackFixture != null && (body == null || body.getWorld() == null)) {
            attackFixture = null;
        }
    }

    @Override
    public void draw(Batch batch) {
        TextureRegion currentFrame;
        if (defeated && type.getAnimations().containsKey("death")) {
            super.draw(batch);
            return;
        } else if (attacking && type.getAnimations().containsKey("attack")) {
            currentFrame = type.getAnimations().get("attack").getKeyFrame(attackStateTime, true);
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
