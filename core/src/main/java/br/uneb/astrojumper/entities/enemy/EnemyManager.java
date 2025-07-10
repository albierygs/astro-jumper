package br.uneb.astrojumper.entities.enemy;

import br.uneb.astrojumper.entities.enemy.factories.EnemyFlyweightFactory;
import br.uneb.astrojumper.entities.enemy.factories.FlyingEnemyFactory;
import br.uneb.astrojumper.entities.enemy.factories.GroundEnemyFactory;
import br.uneb.astrojumper.entities.enemy.movable.MovableFlyingEnemy;
import br.uneb.astrojumper.entities.enemy.movable.MovableGroundEnemy;
import br.uneb.astrojumper.entities.enemy.statics.StaticFlyingEnemy;
import br.uneb.astrojumper.entities.enemy.statics.StaticGroundEnemy;
import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

public class EnemyManager {
    private Array<Enemy> activeEnemies;
    private PlayScreen playScreen;

    public EnemyManager(PlayScreen playScreen) {
        this.playScreen = playScreen;
        activeEnemies = new Array<>();
        loadEnemiesFromMap();
    }

    private void loadEnemiesFromMap() {
        TiledMap map = playScreen.getMap();

        MapLayer groundEnemyLayer = map.getLayers().get("enemies");
        if (groundEnemyLayer != null) {
            for (MapObject object : groundEnemyLayer.getObjects()) {
                Object enemyType = object.getProperties().get("type");

                if (enemyType.toString().equalsIgnoreCase("movable-ground")) {
                    MovableGroundEnemy groundEnemy = new GroundEnemyFactory().createMovableEnemy(playScreen);
                    if (object instanceof RectangleMapObject) {
                        RectangleMapObject rectObject = (RectangleMapObject) object;
                        groundEnemy.body.setTransform(
                            (rectObject.getRectangle().getX() + rectObject.getRectangle().getWidth() / 2) / Constants.PIXELS_PER_METER,
                            (rectObject.getRectangle().getY() + rectObject.getRectangle().getHeight() / 2) / Constants.PIXELS_PER_METER,
                            0
                        );
                    }
                    activeEnemies.add(groundEnemy);
                } else if (enemyType.toString().equalsIgnoreCase("movable-flying")) {
                    MovableFlyingEnemy flyingEnemy = new FlyingEnemyFactory().createMovableEnemy(playScreen);
                    if (object instanceof RectangleMapObject) {
                        RectangleMapObject rectObject = (RectangleMapObject) object;
                        flyingEnemy.body.setTransform(
                            (rectObject.getRectangle().getX() + rectObject.getRectangle().getWidth() / 2) / Constants.PIXELS_PER_METER,
                            (rectObject.getRectangle().getY() + rectObject.getRectangle().getHeight() / 2) / Constants.PIXELS_PER_METER,
                            0
                        );
                        flyingEnemy.body.setGravityScale(0);
                    }
                    activeEnemies.add(flyingEnemy);
                } else if (enemyType.toString().equalsIgnoreCase("static-ground")) {
                    StaticGroundEnemy staticGroundEnemy = new GroundEnemyFactory().createStaticEnemy(playScreen);
                    if (object instanceof RectangleMapObject) {
                        RectangleMapObject rectObject = (RectangleMapObject) object;
                        staticGroundEnemy.body.setTransform(
                            (rectObject.getRectangle().getX() + rectObject.getRectangle().getWidth() / 2) / Constants.PIXELS_PER_METER,
                            (rectObject.getRectangle().getY() + rectObject.getRectangle().getHeight() / 2) / Constants.PIXELS_PER_METER,
                            0
                        );
                    }
                    activeEnemies.add(staticGroundEnemy);
                } else if (enemyType.toString().equalsIgnoreCase("static-flying")) {
                    StaticFlyingEnemy staticFlyingEnemy = new FlyingEnemyFactory().createStaticEnemy(playScreen);
                    if (object instanceof RectangleMapObject) {
                        RectangleMapObject rectObject = (RectangleMapObject) object;
                        staticFlyingEnemy.body.setTransform(
                            (rectObject.getRectangle().getX() + rectObject.getRectangle().getWidth() / 2) / Constants.PIXELS_PER_METER,
                            (rectObject.getRectangle().getY() + rectObject.getRectangle().getHeight() / 2) / Constants.PIXELS_PER_METER,
                            0
                        );
                    }
                    activeEnemies.add(staticFlyingEnemy);
                }
            }
        }
    }

    public void update(float delta) {
        for (int i = activeEnemies.size - 1; i >= 0; i--) {
            Enemy enemy = activeEnemies.get(i);
            enemy.update(delta);

            if (enemy.isDefeated() && enemy.isDeathAnimationFinished()) {
                if (enemy.body != null && playScreen.getWorld() != null) {
                    playScreen.getWorld().destroyBody(enemy.body);
                    enemy.body = null;
                }
                activeEnemies.removeIndex(i);
            }
        }
    }

    public void render(Batch batch) {
        for (Enemy enemy : activeEnemies) {
            enemy.draw(batch);
        }
    }

    public void dispose() {
        for (Enemy enemy : activeEnemies) {
            if (enemy.body != null && playScreen.getWorld() != null) {
                playScreen.getWorld().destroyBody(enemy.body);
            }
        }
        activeEnemies.clear();
        EnemyFlyweightFactory.clear();
    }
}
