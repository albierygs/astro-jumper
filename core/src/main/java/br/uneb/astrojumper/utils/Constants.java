package br.uneb.astrojumper.utils;

public final class Constants {
    public static final int VIRTUAL_WIDTH = 1100;
    public static final int VIRTUAL_HEIGHT = 680;
    public static final float PIXELS_PER_METER = 100f;

    // configurações de bits para gerenciar as colisões de objetos
    public static final short PLAYER_BIT = 0x0001;
    public static final short GROUND_BIT = 0x0002;
    public static final short METEOR_BIT = 0x0004;
    public static final short RAY_BIT = 0x0008;
    public static final short DAMAGE_BIT = 0x0010;
    public static final short FINAL_SPACESHIP_BIT = 0x0020;
    public static final short DESTROYED_BIT = 0x0040;
    public static final short ALL_BIT = -1;
}
