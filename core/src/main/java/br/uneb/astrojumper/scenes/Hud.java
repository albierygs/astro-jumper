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
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Hud implements Disposable {
    private Stage stage;
    private Viewport viewport;
    private PlayScreen playScreen;

    // Campos para os valores do jogo
    private Integer gameTime; // Tempo de jogo (agora será crescente)
    private float timeCount;
    private Integer score; // Score continua sendo uma opção a ser exibida, mas não explicitamente pedida para substituir no topo
    private Integer lives;
    private Integer raysCollected;
    private boolean timeUp; // Manter para controle futuro de limite de tempo, se houver

    // Labels para exibição
    Label gameTimeLabel; // Novo Label para o tempo de jogo
    Label livesLabel;
    Label raysLabel;
    Label pausedLabel;

    // Títulos dos labels
    Label timeTitleLabel;
    Label livesTitleLabel;
    Label raysTitleLabel;

    public Hud(SpriteBatch batch, PlayScreen playScreen) {
        gameTime = 0; // Tempo de jogo começa em zero
        timeCount = 0;
        score = 0; // Manter score, caso queira exibir em outro lugar ou adicionar futuramente
        lives = 3; // Vidas iniciais
        raysCollected = 0; // Raios iniciais
        timeUp = false; // Mantido para futuras implementações de limite de tempo

        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        BitmapFont customFont = AssetLoader.get("orbitron.fnt", BitmapFont.class);
        customFont.getData().setScale(0.4f);
        LabelStyle labelStyle = new LabelStyle(customFont, Color.WHITE);

        // Estilo para a mensagem de pausa (pode ser maior ou de outra cor)
        BitmapFont pauseFont = AssetLoader.get("orbitron.fnt", BitmapFont.class);
        pauseFont.getData().setScale(0.8f);
        LabelStyle pauseLabelStyle = new LabelStyle(pauseFont, Color.YELLOW);

        // Inicializa os labels com seus valores iniciais e títulos
        timeTitleLabel = new Label("TIME", labelStyle);
        gameTimeLabel = new Label(String.format("%03d", gameTime), labelStyle); // Tempo de jogo

        livesTitleLabel = new Label("LIFE", labelStyle);
        livesLabel = new Label(String.format("%01d", lives), labelStyle); // Quantidade de vidas

        raysTitleLabel = new Label("RAY", labelStyle);
        raysLabel = new Label(String.format("%02d", raysCollected), labelStyle); // Quantidade de raios coletados

        // Inicializa o pausedLabel e o torna invisível por padrão
        pausedLabel = new Label("PAUSE", pauseLabelStyle);
        pausedLabel.setVisible(false);
        pausedLabel.setPosition(viewport.getWorldWidth() / 2 - pausedLabel.getWidth() / 2,
            viewport.getWorldHeight() / 2 - pausedLabel.getHeight() / 2);

        // --- Reorganização da Tabela para exibir as novas informações ---
        // Primeira linha: Títulos das informações no topo
        table.add(livesTitleLabel).expandX().padTop(10);
        table.add(raysTitleLabel).expandX().padTop(10);
        table.add(timeTitleLabel).expandX().padTop(10); // Título do tempo

        table.row(); // Próxima linha para os valores

        // Segunda linha: Valores das informações
        table.add(livesLabel).expandX();
        table.add(raysLabel).expandX();
        table.add(gameTimeLabel).expandX(); // Valor do tempo

        stage.addActor(table);
        stage.addActor(pausedLabel);
        this.playScreen = playScreen;
    }

    public void update(float delta) {
        stage.act(delta);

        // O tempo do jogo (crescente) só avança se não estiver pausado
        if (!pausedLabel.isVisible()) {
            timeCount += delta;
            if (timeCount >= 1) { // A cada segundo
                gameTime++;
                gameTimeLabel.setText(String.format("%03d", gameTime));
                timeCount = 0; // Reseta o contador para o próximo segundo

            }
            lives = playScreen.getPlayer().getRemainingLives();
            livesLabel.setText(String.format("%01d", lives));
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // Métodos para atualizar os valores
    public void addRay() {
        raysCollected++;
        raysLabel.setText(String.format("%02d", raysCollected));
    }

    public void removeLife() {
        if (lives > 0) {
            lives--;
            livesLabel.setText(String.format("%01d", lives));
        }
    }

    public void setLifes(int n) {
        this.lives = n;
        livesLabel.setText(String.format("%01d", lives));
    }

    public void addScore(int value) {
        score += value;
        // Se você quiser exibir o score em algum lugar, adicione um Label para ele
        // scoreLabel.setText(String.format("%06d", score));
    }

    // Este método pode ser usado pela PlayScreen para saber o tempo total de jogo
    public int getGameTime() {
        return gameTime;
    }

    /**
     * Define a visibilidade da mensagem de pausa.
     * @param isPaused true para mostrar "PAUSADO", false para esconder.
     */
    public void setPaused(boolean isPaused) {
        pausedLabel.setVisible(isPaused);
    }

    public Stage getStage() {
        return stage;
    }

    public void collectRay() {
        addRay(); // método que realmente incrementa e atualiza o label

    }

}
