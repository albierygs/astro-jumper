package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.entities.meteor.Meteor;
import br.uneb.astrojumper.entities.meteor.MeteorFactory;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class MeteorManager {
    private static final float SPAWN_INTERVAL = 2f; // intervalo entre a queda dos meteoros

    private Array<Meteor> activeMeteors;
    private Array<Vector2> spawnPositions;
    private Random random;
    private float timeToNextSpawn;
    private PlayScreen playScreen;

    public MeteorManager(PlayScreen playScreen) {
        this.playScreen = playScreen;
        activeMeteors = new Array<>();
        spawnPositions = new Array<>();
        random = new Random();
        timeToNextSpawn = SPAWN_INTERVAL;
        loadTiledPositions();
    }

    public void loadTiledPositions() {
        MapLayer meteorsLayer = playScreen.getMap().getLayers().get("meteor-spawns");
        if (meteorsLayer == null) {
            return;
        }

        MapObjects objects = meteorsLayer.getObjects();
        for (MapObject object : objects) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                Vector2 position = new Vector2(rectangle.getX() + rectangle.getWidth() / 2, Constants.VIRTUAL_HEIGHT);
                spawnPositions.add(position);
            }
        }
    }

    public void update(float deltaTime) {
        for (int i = activeMeteors.size - 1; i >= 0; i--) {
            Meteor meteor = activeMeteors.get(i);
            meteor.update(deltaTime);

            if (meteor.isFinished()) {
                playScreen.getWorld().destroyBody(meteor.body);
                activeMeteors.removeIndex(i);
            }
        }

        timeToNextSpawn -= deltaTime;
        if (timeToNextSpawn <= 0 && spawnPositions.size > 0) {
            spawnMeteor();
            timeToNextSpawn = SPAWN_INTERVAL;
        }
    }

    private void spawnMeteor() {
        for (Vector2 position : spawnPositions) {
            Meteor newMeteor = new Meteor(playScreen, position, MeteorFactory.getBaseMeteor(playScreen));
            activeMeteors.add(newMeteor);
        }
    }

    public void render(Batch batch) {
        for (Meteor meteor : activeMeteors) {
            meteor.render(batch);
        }
    }
}
