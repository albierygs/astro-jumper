package br.uneb.astrojumper.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

public class Hud implements Disposable {
    public Stage stage;
//    private final Viewport viewport;
//    private final Integer worldTimer;
//    private final Float timeCount;
//    private final Integer score;
//    private final Label timeLabel;
//    private final Label scoreLabel;
//    private final Label countdownLabel;
//    private final Label levelLabel;
//    private final Label worldLabel;
//    private final Label marioLabel;


    public Hud(final SpriteBatch batch) {
//        worldTimer = 300;
//        timeCount = 0f;
//        score = 0;
//
//        viewport = new FitViewport(MarioBros.VIRTUAL_WIDTH, MarioBros.VIRTUAL_HEIGHT, new OrthographicCamera());
//        stage = new Stage(viewport, batch);
//
//        Table table = new Table();
//        table.top();
//        table.setFillParent(true);
//
//        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(),
//            Color.WHITE));
//        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(),
//            Color.WHITE));
//        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//
//        table.add(marioLabel).expandX().padTop(10);
//        table.add(worldLabel).expandX().padTop(10);
//        table.add(timeLabel).expandX().padTop(10);
//        table.row();
//        table.add(scoreLabel).expandX();
//        table.add(levelLabel).expandX();
//        table.add(countdownLabel).expandX();
//
//        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
