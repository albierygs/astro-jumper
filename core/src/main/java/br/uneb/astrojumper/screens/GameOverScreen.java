package br.uneb.astrojumper.screens;

import br.uneb.astrojumper.AstroJumper;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen implements Screen {

    private AstroJumper game;
    private SpriteBatch batch;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private BitmapFont font;

    public GameOverScreen(AstroJumper game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.gameCam = new OrthographicCamera();
        this.viewport = new FitViewport(
            Constants.VIRTUAL_WIDTH,
            Constants.VIRTUAL_HEIGHT,
            gameCam
        );
        this.font = AssetLoader.get("orbitron.fnt", BitmapFont.class);
        font.setColor(Color.WHITE);
        font.getData().setScale(1.2f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();

        font.draw(batch, "Morreu", 3f, 3f);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
