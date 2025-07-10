package br.uneb.astrojumper.scenes;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud implements Disposable {
    private Stage stage;
    private Viewport viewport;
    private PlayScreen playScreen;

    // Labels para exibição
    Label gameTimeLabel;
    private Sound countdownSound;
    private Sound loseLifeSound;
    private float timeCount;
    private Integer lives;
    private Integer raysCollected;
    private Integer gameTime;
    private Integer totalRays;
    Label livesLabel;
    Label raysLabel;
    Label pausedLabel;

    // Títulos dos labels
    Label timeTitleLabel;
    Label livesTitleLabel;
    Label raysTitleLabel;

    public Hud(SpriteBatch batch, PlayScreen playScreen) {
        totalRays = playScreen.getMap().getLayers().get("rays").getObjects().getCount();
        gameTime = 120;
        timeCount = 0;
        lives = 3;
        raysCollected = 0;

        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        countdownSound = AssetLoader.get("countdown.mp3", Sound.class);
        loseLifeSound = AssetLoader.get("lose-life.wav", Sound.class);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        BitmapFont customFont = AssetLoader.get("orbitron.fnt", BitmapFont.class);
        customFont.getData().setScale(0.4f);
        LabelStyle labelStyle = new LabelStyle(customFont, Color.WHITE);

        BitmapFont pauseFont = AssetLoader.get("orbitron.fnt", BitmapFont.class);
        pauseFont.getData().setScale(0.8f);
        LabelStyle pauseLabelStyle = new LabelStyle(pauseFont, Color.YELLOW);

        timeTitleLabel = new Label("TIME", labelStyle);
        gameTimeLabel = new Label(String.format("%02d:%02d", gameTime / 60, gameTime % 60), labelStyle);

        livesTitleLabel = new Label("LIVES", labelStyle);
        livesLabel = new Label(String.format("%d", lives), labelStyle);

        raysTitleLabel = new Label("RAYS", labelStyle);
        raysLabel = new Label(String.format("%d/%d", raysCollected, totalRays), labelStyle);

        pausedLabel = new Label("PAUSE", pauseLabelStyle);
        pausedLabel.setVisible(false);
        pausedLabel.setPosition(viewport.getWorldWidth() / 2 - pausedLabel.getWidth() / 2,
            viewport.getWorldHeight() / 2 - pausedLabel.getHeight() / 2);

        table.add(livesTitleLabel).expandX().padTop(10);
        table.add(raysTitleLabel).expandX().padTop(10);
        table.add(timeTitleLabel).expandX().padTop(10);

        table.row();

        table.add(livesLabel).expandX();
        table.add(raysLabel).expandX();
        table.add(gameTimeLabel).expandX();

        stage.addActor(table);
        stage.addActor(pausedLabel);
        this.playScreen = playScreen;
    }

    public void update(float delta) {
        stage.act(delta);

        if (!pausedLabel.isVisible()) {
            timeCount += delta;
            if (timeCount >= 1) {
                if (gameTime > 0) {
                    gameTime--;
                } else {
                    playScreen.setGameOver(true);
                }

                gameTimeLabel.setText(String.format("%02d:%02d", gameTime / 60, gameTime % 60));
                timeCount = 0;

                if (gameTime < 10 && gameTime > 0) {
                    gameTimeLabel.setColor(Color.RED);
                } else if (gameTime == 0) {
                    gameTimeLabel.setColor(Color.RED);
                } else {
                    gameTimeLabel.setColor(Color.WHITE);
                }

                if (gameTime == 9) {
                    countdownSound.play(1.0f);
                }
            }
            lives = playScreen.getPlayer().getRemainingLives();
            livesLabel.setText(String.format("%d", lives));
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setLifes(int n) {
        this.lives = n;
        livesLabel.setText(String.format("%d", Math.max(0, lives)));

        loseLifeSound.play(1.0f);

        livesLabel.clearActions();
        livesLabel.addAction(Actions.sequence(
            Actions.moveBy(5, 0, 0.05f),
            Actions.moveBy(-10, 0, 0.1f),
            Actions.moveBy(5, 0, 0.05f)
        ));

        livesLabel.addAction(Actions.sequence(
            Actions.color(Color.RED, 0.1f),
            Actions.delay(0.1f),
            Actions.color(Color.WHITE, 0.3f)
        ));
    }

    public void setPaused(boolean isPaused) {
        pausedLabel.setVisible(isPaused);
    }

    public Stage getStage() {
        return stage;
    }

    public Integer getGameTime() {
        return gameTime;
    }

    public void collectRay() {
        raysCollected++;
        raysLabel.setText(String.format("%d/%d", raysCollected, totalRays));
    }

}
