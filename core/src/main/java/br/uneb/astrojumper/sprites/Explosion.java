package br.uneb.astrojumper.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private final Animation<TextureRegion> animation;
    private float stateTime;
    private final Vector2 position;
    private boolean finished;

    public Explosion(float x, float y) {
        Texture sheet = new Texture("./textures/explosion.png"); // Carregando o spritesheet

        int rows = 1; // Linhas do sprite
        int columns = 8; // Colunas do sprite

        TextureRegion[][] textureRegions2d = TextureRegion.split(sheet, sheet.getWidth() / columns,
            sheet.getHeight() / rows); // Divide o sprite em quadros

        // Transforma a matriz 2D em 1D
        TextureRegion[] textureRegions = new TextureRegion[rows * columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                textureRegions[index++] = textureRegions2d[i][j];
            }
        }

        animation = new Animation<>(0.1f, textureRegions); // Cria a animação

        position = new Vector2(x, y);
        stateTime = 0f;
        finished = false;
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        if (animation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(Batch batch) {
        if (!finished) {
            TextureRegion currentFrame = animation.getKeyFrame(stateTime, false);
            batch.draw(currentFrame, position.x, position.y);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
