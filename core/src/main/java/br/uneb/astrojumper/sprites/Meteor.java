package br.uneb.astrojumper.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Meteor {
    private static final float GRAVITY = -500f;
    private static final float GROUND_Y = 70f;

    private Vector2 position;
    private Vector2 velocity;
    private Texture texture;

    private boolean exploding;
    private boolean finished;
    private Explosion explosion;
    private Sound sound;


    public Meteor(float x, float y) {
        this.texture = new Texture("./textures/meteor.png");
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, -200);
        this.exploding = false;
        this.finished = false;
        this.explosion = new Explosion(position.x, position.y);
        this.sound = Gdx.audio.newSound(Gdx.files.internal("./sounds/meteor-impact.mp3"));
    }

    public void update(float deltaTime) {
        if (finished) return;

        if (!exploding) {
            velocity.y += GRAVITY * deltaTime;
            position.mulAdd(velocity, deltaTime);

            if (position.y <= GROUND_Y) {
                position.y = GROUND_Y;
                exploding = true;
                sound.play(1.0f);
                explosion = new Explosion(position.x, GROUND_Y);
            }
        } else {
            explosion.update(deltaTime);
            if (explosion.isFinished()) {
                finished = true;
            }
        }
    }

    public void render(Batch batch) {
        if (finished) return;

        if (!exploding) {
            batch.draw(texture, position.x, position.y);
        } else {
            explosion.render(batch);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public Vector2 getPosition() {
        return position;
    }
}
