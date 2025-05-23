package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

public abstract class InteractiveTileObject extends TileObject {
    public InteractiveTileObject(World world, TiledMap map, MapObject bounds) {
        super(world, map, bounds);
    }

    public abstract void colide();

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
        float tileSize = 68f;

        int cellX = (int) (body.getPosition().x * Constants.PIXELS_PER_METER / tileSize);
        int cellY = (int) (body.getPosition().y * Constants.PIXELS_PER_METER / tileSize);

        if (cellX < 0 || cellX >= layer.getWidth() || cellY < 0 || cellY >= layer.getHeight()) {
            return null;
        }

        return layer.getCell(cellX, cellY);
    }
}
