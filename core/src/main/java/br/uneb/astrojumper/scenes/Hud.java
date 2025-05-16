package br.uneb.astrojumper.scenes;

import br.uneb.astrojumper.AstroJumper;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;
import br.uneb.astrojumper.utils.FontManager;


//import java.awt.*;

public class Hud implements Disposable {
    public Stage stage;
    private final Integer worldTimer;
    private final Float timeCount;
    private final Integer score;

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label astrojumperLabel;



//    private final Label timeLabel;
//    private final Label scoreLabel;
//    private final Label countdownLabel;
//    private final Label levelLabel;
//    private final Label worldLabel;
//    private final Label marioLabel;


    public Hud(final SpriteBatch batch, Viewport viewport, Integer worldTimer, Float timeCount, Integer score) {
      worldTimer = 300;
      timeCount = (float) 0;
      score = 0;

        viewport = new FitViewport(AstroJumper.V_WIDTH, AstroJumper.V_HEIGHT,/*MarioBros.VIRTUAL_WIDTH, MarioBros.VIRTUAL_HEIGHT,*/ new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        FontManager.load(); // garante que a fonte esteja carregada
        BitmapFont customFont = FontManager.getFont();
        customFont.getData().setScale(0.4f); // ou 0.3f, ajuste como quiser
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
        this.worldTimer = worldTimer;
        this.timeCount = timeCount;
        this.score = score;
    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
