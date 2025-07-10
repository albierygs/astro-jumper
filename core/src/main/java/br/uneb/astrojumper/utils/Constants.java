package br.uneb.astrojumper.utils;

public final class Constants {
    public static final int VIRTUAL_WIDTH = 1100;
    public static final int VIRTUAL_HEIGHT = 680;
    public static final float PIXELS_PER_METER = 100f;

    // configurações de bits para gerenciar as colisões de objetos
    public static final short PLAYER_BIT = 1;
    public static final short GROUND_BIT = 2;
    public static final short COLLISION_BIT = 2;
    public static final short METEOR_BIT = 4;
    public static final short RAY_BIT = 8;
    public static final short DAMAGE_BIT = 16;
    public static final short FINAL_SPACESHIP_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short DESTROYED_BIT = 128;
    public static final short COLLISION_ENEMY_BIT = 256;
    public static final short PROJECTILE_BIT = 512;
    public static final short ALL_BIT = -1;
}
