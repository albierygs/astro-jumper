package br.uneb.astrojumper.entities.meteor;

import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BaseMeteor {
    private final float fallVelocity = -5f;

    private Sound impactSound;
    private Animation<TextureRegion> explosionAnimation;

    private Astronaut player;

    public BaseMeteor(PlayScreen playScreen) {
        this.player = playScreen.getPlayer();

        this.impactSound = AssetLoader.get("meteor-impact.mp3", Sound.class);

        Texture explosionTexture = AssetLoader.get("explosion.png", Texture.class);

        int rows = 1;
        int columns = 8;
        TextureRegion[][] regions2D = TextureRegion.split(explosionTexture, explosionTexture.getWidth() / columns, explosionTexture.getHeight() / rows);

        TextureRegion[] regions = new TextureRegion[rows * columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                regions[index++] = regions2D[i][j];
            }
        }
        this.explosionAnimation = new Animation<>(0.05f, regions);
    }

    public Sound getImpactSound() {
        return impactSound;
    }

    public Animation<TextureRegion> getExplosionAnimation() {
        return explosionAnimation;
    }

    public Astronaut getPlayer() {
        return player;
    }

    public float getFallVelocity() {
        return fallVelocity;
    }
}
