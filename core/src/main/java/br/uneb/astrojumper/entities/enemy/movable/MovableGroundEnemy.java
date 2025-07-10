package br.uneb.astrojumper.entities.enemy.movable;

import br.uneb.astrojumper.entities.enemy.factories.EnemyFlyweightFactory;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class MovableGroundEnemy extends MovableEnemyBase {
    public MovableGroundEnemy(PlayScreen playScreen) {
        super(EnemyFlyweightFactory.getEnemyType("movable-ground", playScreen), 25);
        fixture.setUserData(this);
        defineMovableAnimation();
        setBounds(0, 0, 110 / Constants.PIXELS_PER_METER, 79 / Constants.PIXELS_PER_METER);
    }

    @Override
    public void defineMovableAnimation() {
        Texture texture = AssetLoader.get("movable-ground-enemy-walk.png", Texture.class);

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

        texture = AssetLoader.get("movable-ground-enemy-death.png", Texture.class);

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
