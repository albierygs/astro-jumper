package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.entities.Meteor;
import br.uneb.astrojumper.entities.Ray;
import br.uneb.astrojumper.tiles.factories.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorldObjectsManager {
    private Array<ITileObject> tileObjects;

    // mapa dos nomes das camadas e suas fábricas
    private Map<String, TileFactory> tileFactories;

    public WorldObjectsManager(World world, TiledMap map) {
        tileObjects = new Array<>();
        tileFactories = new HashMap<>();

        // registrando as fábricas para cada tipo de camada
        tileFactories.put("ground", new StaticTileFactory());
        tileFactories.put("collision", new StaticTileFactory());
        tileFactories.put("damage", new DamageTileFactory());
        tileFactories.put("rays", new RayTileFactory());
        tileFactories.put("final", new FinalSpaceshipTileFactory());

        // Processar camadas do mapa
        for (MapLayer layer : map.getLayers()) {
            // a camada dos meteoros é gerenciado em outra classe
            if (layer.getName().equalsIgnoreCase("meteor-spawns")) {
                continue;
            }

            // criando os tiles para cada camada
            TileFactory factory = tileFactories.get(layer.getName().toLowerCase());
            if (factory != null) {
                for (MapObject object : layer.getObjects()) {
                    ITileObject tile = factory.createTile(world, map, object);
                    tileObjects.add(tile);
                }
            } else {
                for (MapObject object : layer.getObjects()) {
                    ITileObject tile = new StaticTileFactory().createTile(world, map, object);
                    tileObjects.add(tile);
                }
            }
        }
    }

    public void render(Batch batch) {
        for (ITileObject tile : tileObjects) {
            tile.render(batch);
        }
    }

    public void update(float delta) {
        Iterator<ITileObject> iterator = tileObjects.iterator();
        while (iterator.hasNext()) {
            ITileObject tile = iterator.next();
            tile.update(delta);
            if (tile instanceof Ray && ((Ray) tile).isFinished()) {
                tile.dispose();
                iterator.remove();
            }

            if (tile instanceof Meteor && ((Meteor) tile).isFinished()) { //
                tile.dispose();
                iterator.remove();
            }
        }
    }

    public void dispose() {
        for (ITileObject tile : tileObjects) {
            tile.dispose();
        }
        tileObjects.clear();
    }
}
