package br.uneb.astrojumper.screens;

import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelSelectScreen implements Screen {

    private final Music victoryMusic;
    private AstroJumper game;
    private Stage stage;
    private Viewport viewport;
    private Texture background;
    private SpriteBatch batch;
    private Skin skin;
    private Sound clickSound;

    private Preferences prefs;

    public LevelSelectScreen(AstroJumper game) {
        this.game = game;
        prefs = Gdx.app.getPreferences("AstroJumperPrefs");

        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        background = AssetLoader.get("background_menu.png", Texture.class);
        batch = new SpriteBatch();

        skin = new Skin();

        BitmapFont font = AssetLoader.get("Neuropol.fnt", BitmapFont.class);
        skin.add("default-font", font);

        Drawable buttonUp = createButtonDrawable(new Color(0.85f, 0.85f, 0.85f, 1));
        Drawable buttonDown = createButtonDrawable(new Color(0.6f, 0.6f, 0.6f, 1));
        Drawable buttonDisabled = createButtonDrawable(new Color(0.4f, 0.4f, 0.4f, 1));

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = buttonUp;
        buttonStyle.down = buttonDown;
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.DARK_GRAY;

        TextButton.TextButtonStyle buttonDisabledStyle = new TextButton.TextButtonStyle();
        buttonDisabledStyle.up = buttonDisabled;
        buttonDisabledStyle.down = buttonDisabled;
        buttonDisabledStyle.font = font;
        buttonDisabledStyle.fontColor = Color.GRAY;

        skin.add("default", buttonStyle);
        skin.add("disabled", buttonDisabledStyle);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = font;
        titleStyle.fontColor = Color.WHITE;
        skin.add("title", titleStyle);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("SELECT LEVEL", skin, "title");

        // Botões dos níveis
        TextButton level1 = new TextButton("Level 1", skin);
        TextButton level2 = new TextButton("Level 2", skin);
        TextButton level3 = new TextButton("Level 3", skin);
        TextButton backButton = new TextButton("Back", skin);

        clickSound = AssetLoader.get("click.mp3", Sound.class);
        victoryMusic = AssetLoader.get("VICTORY.mp3", Music.class);
        victoryMusic.setLooping(false);
        victoryMusic.setVolume(0.6f);
        victoryMusic.play();

        boolean level1Completed = prefs.getBoolean("level1_completed", false);

        if (!level1Completed) {
            level2.setStyle(skin.get("disabled", TextButton.TextButtonStyle.class));
            level2.setDisabled(true);
            level2.getLabel().setColor(Color.GRAY);

            level3.setStyle(skin.get("disabled", TextButton.TextButtonStyle.class));
            level3.setDisabled(true);
            level3.getLabel().setColor(Color.GRAY);
        }

        level1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play(1.4f);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        victoryMusic.stop();
                        game.setScreen(new PlayScreen(game, AssetLoader.get("level1.tmx", TiledMap.class)));
                    }
                }, 0.3f);
            }
        });

        if (level1Completed) {
            level2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clickSound.play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            victoryMusic.stop();
                            game.setScreen(new PlayScreen(game, AssetLoader.get("level2.tmx", TiledMap.class)));
                            dispose();
                        }
                    }, 0.3f);
                }
            });

            level3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clickSound.play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            victoryMusic.stop();
                            game.setScreen(new PlayScreen(game, AssetLoader.get("level3.tmx", TiledMap.class)));
                            dispose();
                        }
                    }, 0.3f);
                }
            });
        }

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickSound.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        victoryMusic.stop();
                        game.setScreen(new MainMenuScreen(game));
                        dispose();
                    }
                }, 0.3f);
            }
        });

        table.add(title).padBottom(80).row();
        table.add(level1).width(160).height(45).padBottom(15).row();
        table.add(level2).width(160).height(45).padBottom(15).row();
        table.add(level3).width(160).height(45).padBottom(15).row();
        table.add(backButton).width(160).height(45).padBottom(15).row();
    }

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

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
        if (batch != null) batch.dispose();
        if (background != null) background.dispose();
        if (skin != null) skin.dispose();
        if (victoryMusic != null) victoryMusic.dispose();
        // Não chamamos clickSound.dispose() aqui para evitar descarregar som compartilhado
    }
}
