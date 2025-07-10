package br.uneb.astrojumper.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetLoader {
    private static AssetManager manager;

    public static void load() {
        if (manager == null) {
            manager = new AssetManager();
            manager.setLoader(TiledMap.class, new TmxMapLoader());

            // carregando os mapas
            manager.load("maps/level1.tmx", TiledMap.class);
            manager.load("maps/level2.tmx", TiledMap.class);
            manager.load("maps/level3.tmx", TiledMap.class);

            // carregando as texturas
            manager.load("textures/astronaut-idle.png", Texture.class);
            manager.load("textures/astronaut-walk.png", Texture.class);
            manager.load("textures/astronaut-jump.png", Texture.class);
            manager.load("textures/astronaut-dead.png", Texture.class);
            manager.load("textures/explosion.png", Texture.class);
            manager.load("textures/meteor.png", Texture.class);
            manager.load("textures/bolt-sheet.png", Texture.class);
            manager.load("textures/background_menu.png", Texture.class);
            manager.load("textures/background_gameover.png", Texture.class);
            manager.load("textures/background_parabens.png", Texture.class);
            manager.load("textures/spaceship.png", Texture.class);
            manager.load("textures/lock_icon.png", Texture.class);
            manager.load("textures/movable-flying-enemy-fly.png", Texture.class);
            manager.load("textures/movable-flying-enemy-death.png", Texture.class);
            manager.load("textures/movable-ground-enemy-walk.png", Texture.class);
            manager.load("textures/movable-ground-enemy-death.png", Texture.class);
            manager.load("textures/static-ground-enemy-idle1.png", Texture.class);
            manager.load("textures/static-ground-enemy-idle2.png", Texture.class);
            manager.load("textures/static-ground-enemy-idle3.png", Texture.class);
            manager.load("textures/static-ground-enemy-death1.png", Texture.class);
            manager.load("textures/static-ground-enemy-death2.png", Texture.class);
            manager.load("textures/static-ground-enemy-death3.png", Texture.class);
            manager.load("textures/static-ground-enemy-death4.png", Texture.class);
            manager.load("textures/static-ground-enemy-death5.png", Texture.class);
            manager.load("textures/static-ground-enemy-attack1.png", Texture.class);
            manager.load("textures/static-ground-enemy-attack2.png", Texture.class);
            manager.load("textures/static-ground-enemy-attack3.png", Texture.class);
            manager.load("textures/static-ground-enemy-attack4.png", Texture.class);
            manager.load("textures/static-flying-enemy.png", Texture.class);
            manager.load("textures/spaceship-bullet.png", Texture.class);
            manager.load("textures/spaceship-bullet-explosion.png", Texture.class);

            // carregando os sons
            manager.load("sounds/meteor-impact.mp3", Sound.class);
            manager.load("sounds/ray-collect.mp3", Sound.class);
            manager.load("sounds/click.mp3", Sound.class);
            manager.load("sounds/menu_music.mp3", Music.class);
            manager.load("sounds/game-music.mp3", Music.class);
            manager.load("sounds/GAMEOVER.wav", Music.class);
            manager.load("sounds/VICTORY.mp3", Music.class);
            manager.load("sounds/laser-attack.mp3", Sound.class);
            manager.load("sounds/countdown.mp3", Sound.class);
            manager.load("sounds/lose-life.wav", Sound.class);


        //carregando as fontes
        manager.load("fonts/orbitron.fnt", BitmapFont.class);
        manager.load("fonts/Neuropol.fnt", BitmapFont.class);

            manager.finishLoading();
        } else {
            manager.update();
            manager.finishLoading();
        }
    }

    public static <T> T get(String fileName, Class<T> type) {
        String path = "";
        if (type == Texture.class) {
            path = "textures/";
        } else if (type == TiledMap.class) {
            path = "maps/";
        } else if (type == Sound.class || type == Music.class) {
            path = "sounds/";
        } else if (type == BitmapFont.class) {
            path = "fonts/";
        }
        return manager.get(path + fileName, type);
    }

    public static void dispose() {
        manager.dispose();
    }
}
