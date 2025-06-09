package br.uneb.astrojumper.scenes;

import br.uneb.astrojumper.screens.PlayScreen;
import br.uneb.astrojumper.utils.AssetLoader;
import br.uneb.astrojumper.utils.Constants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class Hud implements Disposable {
    private Stage stage;
    private Viewport viewport;
    private final Integer worldTimer;
    private final Float timeCount;
    private final Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label astrojumperLabel;


    public Hud(SpriteBatch batch, PlayScreen playScreen) {
        worldTimer = 300;
        timeCount = (float) 0;
        score = 0;

        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        BitmapFont customFont = AssetLoader.get("orbitron.fnt", BitmapFont.class);
        customFont.getData().setScale(0.4f);
        LabelStyle labelStyle = new LabelStyle(customFont, Color.WHITE);

        countdownLabel = new Label(String.format("%03d", worldTimer), labelStyle);
        scoreLabel = new Label(String.format("%06d", score), labelStyle);
        timeLabel = new Label("TIME", labelStyle);
        levelLabel = new Label("1-1", labelStyle);
        worldLabel = new Label("WORLD", labelStyle);
        astrojumperLabel = new Label("ASTRO JUMPER", labelStyle);




        table.add((Actor) astrojumperLabel).expandX().padTop(10);
        table.add((Actor) worldLabel).expandX().padTop(10);
        table.add((Actor) timeLabel).expandX().padTop(10);
        table.row();
        table.add((Actor) scoreLabel).expandX();
        table.add((Actor) levelLabel).expandX();
        table.add((Actor) countdownLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // atualiza quantos raios foram coletados
    public void collectRay() {
        System.out.println("Mais um raio");
    }

    public void setLifes(int n) {
        System.out.println("Número de vidas " + n);
    }

    public Stage getStage() {
        return stage;
    }
}
