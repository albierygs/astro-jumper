package br.uneb.astrojumper.entities;

import br.uneb.astrojumper.tiles.ITileObject;
import br.uneb.astrojumper.tiles.TileObject;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ray extends TileObject implements ITileObject {
    private Animation<TextureRegion> animation;
    private boolean collected;
    private boolean finished;
    private float stateTime;
    private Vector2 position;

    public Ray(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);

        // configurações do body do raio
        body.setType(BodyDef.BodyType.StaticBody);

        // configurações dos bits do raio
        fixture.getFilterData().categoryBits = Constants.RAY_BIT;
        fixture.getFilterData().maskBits = Constants.PLAYER_BIT;

        Texture texture = AssetLoader.get("bolt-sheet.png", Texture.class);

        // carregando a animação do raio
        int rows = 3;
        int columns = 4;

        TextureRegion[][] regions2D = TextureRegion.split(texture, texture.getWidth() / columns, texture.getHeight() / rows);

        TextureRegion[] regions = new TextureRegion[rows * columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                regions[index++] = regions2D[i][j];
            }
        }

        animation = new Animation<>(0.05f, regions);
        stateTime = 0f;
        finished = false;
        collected = false;

        Rectangle rect = ((RectangleMapObject) bounds).getRectangle();

        // posição que o raio vai aparecer
        position = new Vector2(
            (rect.getX() + rect.getWidth() / 2),
            (rect.getY() + rect.getHeight() / 2)
        );
    }

    private void collect() {
        collected = true;
        stateTime = 0;
        setCategoryFilter(Constants.DESTROYED_BIT); // destruindo o raio depois da colisão
    }

    @Override
    public void render(Batch batch) {
        if (collected && !finished) {
            TextureRegion frame = animation.getKeyFrame(stateTime);

            float width = frame.getRegionWidth() / Constants.PIXELS_PER_METER;
            float height = frame.getRegionHeight() / Constants.PIXELS_PER_METER;

            batch.draw(
                frame,
                position.x / Constants.PIXELS_PER_METER - width / 2f,
                position.y / Constants.PIXELS_PER_METER - height / 2f,
                width,
                height
            );
        }
    }

    @Override
    public void update(float delta) {
        if (collected && !finished) {
            stateTime += delta;
            if (animation.isAnimationFinished(stateTime)) {
                finished = true;
            }
        }
    }

    @Override
    public void colide() {
        collect();

        // removendo o tile do mapa após a colisão
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
        if (layer != null && bounds instanceof RectangleMapObject) {
            Rectangle rect = ((RectangleMapObject) bounds).getRectangle();
            int cellX = (int) (rect.getX() / layer.getTileWidth());
            int cellY = (int) (rect.getY() / layer.getTileHeight());
            layer.setCell(cellX, cellY, null);
        }
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void dispose() {
        if (body != null) world.destroyBody(body);
    }
}
