package br.uneb.astrojumper.entities.enemy.movable;

import br.uneb.astrojumper.entities.enemy.factories.EnemyFlyweightFactory;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class MovableFlyingEnemy extends MovableEnemyBase {
    public MovableFlyingEnemy(PlayScreen playScreen) {
        super(EnemyFlyweightFactory.getEnemyType("movable-flying", playScreen), 25);
        fixture.setUserData(this);
        defineMovableAnimation();
        setBounds(0, 0, 109 / Constants.PIXELS_PER_METER, 83 / Constants.PIXELS_PER_METER);
        body.setGravityScale(0);
    }

    @Override
    public void defineMovableAnimation() {
        Texture texture = AssetLoader.get("movable-flying-enemy-fly.png", Texture.class);

        int rows = 3;
        int columns = 6;

        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        TextureRegion[] frames = new TextureRegion[rows * columns];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        Animation<TextureRegion> walkAnimation = new Animation<>(0.1f, frames);

        texture = AssetLoader.get("movable-flying-enemy-death.png", Texture.class);

        tmp = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);
        frames = new TextureRegion[rows * columns];

        index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        Animation<TextureRegion> deathAnimation = new Animation<>(0.05f, frames);

        this.type.setAnimations(new HashMap<String, Animation<TextureRegion>>() {{
            put("walk", walkAnimation);
            put("death", deathAnimation);
        }});
    }
}
