package br.uneb.astrojumper.screens;

import br.uneb.astrojumper.AstroJumper;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {

    private AstroJumper game;
    private Stage stage;
    private Viewport viewport;

    private Texture background;
    private SpriteBatch batch;

    private Sound clickSound;
    private Music menuMusic;

    private Skin skin;

    public MainMenuScreen(AstroJumper game) {
        this.game = game;

        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        background = AssetLoader.get("background_menu.png", Texture.class);
        batch = new SpriteBatch();

        skin = new Skin();

        // Fonte futurista
        BitmapFont font = AssetLoader.get("Neuropol.fnt", BitmapFont.class);
        skin.add("default-font", font);

        // Criar botão programaticamente (sem PNG)
        Drawable buttonUp = createButtonDrawable(new Color(0.85f, 0.85f, 0.85f, 1));  // Cinza claro
        Drawable buttonDown = createButtonDrawable(new Color(0.6f, 0.6f, 0.6f, 1));   // Cinza escuro ao pressionar

        // Estilo do botão
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.up = buttonUp;
        buttonStyle.down = buttonDown;

        buttonStyle.font = font;
        buttonStyle.fontColor = Color.DARK_GRAY;

        skin.add("default", buttonStyle);

        // Estilo do título
        LabelStyle titleStyle = new LabelStyle();
        titleStyle.font = font;
        titleStyle.fontColor = Color.WHITE;
        skin.add("title", titleStyle);

        // Layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("ASTRO JUMPER", skin, "title");

        TextButton startButton = new TextButton("Play", skin);
        TextButton exitButton = new TextButton("Quit", skin);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new PlayScreen(game, AssetLoader.get("level1.tmx", TiledMap.class)));
                        dispose();
                    }
                }, 0.3f);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                Gdx.app.exit();
            }
        });

        table.add(title).padBottom(80).row();
        table.add(startButton).width(160).height(45).padBottom(15).row();
        table.add(exitButton).width(160).height(45).padBottom(15).row();

        // Som
        clickSound = AssetLoader.get("click.wav", Sound.class);
        menuMusic = AssetLoader.get("menu_music.mp3", Music.class);
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();
    }

    /** Cria uma textura simples de botão com cor e bordas arredondadas */
    private Drawable createButtonDrawable(Color color) {
        int width = 160;
        int height = 45;
        int radius = 15;

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(radius, 0, width - 2 * radius, height);
        pixmap.fillRectangle(0, radius, width, height - 2 * radius);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width - radius, radius, radius);
        pixmap.fillCircle(radius, height - radius, radius);
        pixmap.fillCircle(width - radius, height - radius, radius);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
       // background.dispose();
        batch.dispose();
        clickSound.dispose();
        menuMusic.dispose();
    }
}
