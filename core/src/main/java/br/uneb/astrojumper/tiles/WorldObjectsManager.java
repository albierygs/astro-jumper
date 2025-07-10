package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.entities.FinalSpaceship;
import br.uneb.astrojumper.entities.Ray;
import br.uneb.astrojumper.entities.meteor.Meteor;
import br.uneb.astrojumper.entities.meteor.MeteorFactory;
import br.uneb.astrojumper.screens.PlayScreen;
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

    private MeteorManager meteorManager;

    private FinalSpaceship finalSpaceship;

    public WorldObjectsManager(PlayScreen playScreen) {
        TiledMap map = playScreen.getMap();
        World world = playScreen.getWorld();

        tileObjects = new Array<>();
        tileFactories = new HashMap<>();

        // registrando as fábricas para cada tipo de camada
        tileFactories.put("ground", new GroundTileFactory());
        tileFactories.put("collision", new CollisionTileFactory());
        tileFactories.put("damage", new DamageTileFactory());
        tileFactories.put("rays", new RayTileFactory());
        tileFactories.put("final", new FinalSpaceshipTileFactory());

        // Processar camadas do mapa
        for (MapLayer layer : map.getLayers()) {
            // a camada dos meteoros é gerenciado em outra classe
            if (layer.getName().equalsIgnoreCase("meteor-spawns") || layer.getName().equalsIgnoreCase("begin") || layer.getName().equalsIgnoreCase("enemies")) {
                continue;
            }

            // criando os tiles para cada camada
            TileFactory factory = tileFactories.get(layer.getName().toLowerCase());
            if (factory != null) {
                for (MapObject object : layer.getObjects()) {
                    ITileObject tile = factory.createTile(playScreen, object);
                    tileObjects.add(tile);

                    if (tile instanceof FinalSpaceship) {
                        this.finalSpaceship = (FinalSpaceship) tile;
                    }
                }
            } else {
                for (MapObject object : layer.getObjects()) {
                    ITileObject tile = new GroundTileFactory().createTile(playScreen, object);
                    tileObjects.add(tile);
                }
            }
        }

        meteorManager = new MeteorManager(playScreen);
    }

    public void render(Batch batch) {
        for (ITileObject tile : tileObjects) {
            tile.render(batch);
        }
        meteorManager.render(batch);
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
        meteorManager.update(delta);
    }

    public void dispose() {
        for (ITileObject tile : tileObjects) {
            tile.dispose();
        }
        tileObjects.clear();
        MeteorFactory.clear();
    }

    public FinalSpaceship getFinalSpaceship() {
        return finalSpaceship;
    }
}
