package br.uneb.astrojumper.entities.meteor;

import br.uneb.astrojumper.screens.PlayScreen;

import java.util.HashMap;
import java.util.Map;

public class MeteorFactory {
    private static Map<String, BaseMeteor> meteor = new HashMap<>();

    public static BaseMeteor getBaseMeteor(PlayScreen playScreen) {
        BaseMeteor baseMeteor = meteor.get("meteor");
        if (baseMeteor == null) {
            baseMeteor = new BaseMeteor(playScreen);
            meteor.put("meteor", baseMeteor);
        }
        return baseMeteor;
    }

    public static void clear() {
        meteor.clear();
    }
}
