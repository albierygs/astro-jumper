package br.uneb.astrojumper.tiles;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface ITileObject {
    void colide();

    void update(float delta);

    void render(Batch batch);

    void dispose();
}
