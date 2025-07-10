package br.uneb.astrojumper.entities.enemy;

import br.uneb.astrojumper.screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Map;

public class EnemyType {
    private String name;
    private Map<String, Animation<TextureRegion>> animations;
    private PlayScreen playScreen;

    public EnemyType(String name, Map<String, Animation<TextureRegion>> animations, PlayScreen playScreen) {
        this.name = name;
        this.animations = animations;
        this.playScreen = playScreen;
    }

    public Map<String, Animation<TextureRegion>> getAnimations() {
        return animations;
    }

    public void setAnimations(Map<String, Animation<TextureRegion>> animations) {
        this.animations = animations;
    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }
}
