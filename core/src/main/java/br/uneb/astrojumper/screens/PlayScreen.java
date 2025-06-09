package br.uneb.astrojumper.screens;
import br.uneb.astrojumper.screens.EndLevelScreen;
import br.uneb.astrojumper.AstroJumper;
import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.scenes.Hud;
import br.uneb.astrojumper.tiles.WorldObjectsManager;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.CollisionListener;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {
    private final AstroJumper game;
    private final SpriteBatch batch;
    private final OrthographicCamera gameCam;
    private final Viewport viewport;
    private final Hud hud;

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private World world;
    private final Astronaut player;
    private Box2DDebugRenderer box2DRenderer;

    private WorldObjectsManager worldObjectsManager;

    private Music music;

    private boolean gameOver;
    private boolean completed;

    public PlayScreen(final AstroJumper game, TiledMap mapLevel) {
        this.game = game;
        this.gameOver = false;
        this.completed = false;

        batch = new SpriteBatch();
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(
            Constants.VIRTUAL_WIDTH / Constants.PIXELS_PER_METER,
            Constants.VIRTUAL_HEIGHT / Constants.PIXELS_PER_METER,
            gameCam
        );
        hud = new Hud(batch, this);

        map = mapLevel;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PIXELS_PER_METER);

        gameCam.position.set((float) viewport.getWorldWidth() / 2, (float) viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        box2DRenderer = new Box2DDebugRenderer();

        player = new Astronaut(this);

        worldObjectsManager = new WorldObjectsManager(this);

        world.setContactListener(new CollisionListener());

        music = AssetLoader.get("game-music.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        update(delta);

        if (gameOver && !completed) {
            player.update(delta);

            if (player.isDeadAnimationFinished()) {
                game.setScreen(new GameOverScreen(game));
                dispose();
                return;
            }
        } else if (completed) {
            game.setScreen(new EndLevelScreen(game));
            dispose();
            return;
        }

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        gameCam.update();

        renderer.render();

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();

        worldObjectsManager.render(batch);
        player.draw(batch);

        batch.end();

        box2DRenderer.render(world, gameCam.combined);

        batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        map.dispose();
        renderer.dispose();
        hud.dispose();
        worldObjectsManager.dispose();
        player.dispose();
        world.dispose();
        box2DRenderer.dispose();
        music.dispose();
    }

    public void update(float delta) {
        if (!completed) {
            player.update(delta);

            world.step(1 / 60f, 6, 2);

            gameCam.position.x = player.getBody().getPosition().x;

            gameCam.update();
            renderer.setView(gameCam);

            worldObjectsManager.update(delta);
        }
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public Astronaut getPlayer() {
        return player;
    }

    public Hud getHud() {
        return hud;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
