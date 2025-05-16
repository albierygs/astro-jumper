package br.uneb.astrojumper.screens;

import br.uneb.astrojumper.AstroJumper;
import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.entities.Meteor;
import br.uneb.astrojumper.scenes.Hud;
import br.uneb.astrojumper.tiles.WorldObjectsCreator;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

/** First screen of the application. Displayed after the application is created. */
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

    private Meteor meteor;

    public PlayScreen(final AstroJumper game, TiledMap mapLevel) {
        this.game = game;
        batch = new SpriteBatch();
        gameCam = new OrthographicCamera();
        viewport = new FitViewport(Constants.VIRTUAL_WIDTH / Constants.PIXELS_PER_METER, Constants.VIRTUAL_HEIGHT / Constants.PIXELS_PER_METER, gameCam);
        hud = new Hud(batch, new FitViewport(AstroJumper.V_WIDTH, AstroJumper.V_HEIGHT, new OrthographicCamera()), 300, 0f, 0);


        map = mapLevel;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PIXELS_PER_METER);

        gameCam.position.set((float) viewport.getWorldWidth() / 2, (float) viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        box2DRenderer = new Box2DDebugRenderer();

        player = new Astronaut(world);

        new WorldObjectsCreator(world, map);

        meteor = new Meteor(900, Constants.VIRTUAL_HEIGHT);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        update(delta);

        ScreenUtils.clear(Color.BLACK);

        renderer.render();

        box2DRenderer.render(world, gameCam.combined);

        batch.begin();

        meteor.update(delta);
        meteor.render(batch);

        batch.end();

        viewport.apply();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
        world.dispose();
        box2DRenderer.dispose();
        // hud.dispose();
    }

    public void update(float delta) {
        handleInput(delta);

        world.step(1 / 60f, 6, 2);

        gameCam.position.x = player.getBody().getPosition().x;

        gameCam.update();
        renderer.setView(gameCam);
    }

    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.getBody().applyLinearImpulse(new Vector2(0, 4f), player.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.getBody().getLinearVelocity().x <= 2) {
            player.getBody().applyLinearImpulse(new Vector2(0.5f, 0), player.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.getBody().getLinearVelocity().x >= -2) {
            player.getBody().applyLinearImpulse(new Vector2(-0.1f, 0), player.getBody().getWorldCenter(), true);
        }
    }
}
