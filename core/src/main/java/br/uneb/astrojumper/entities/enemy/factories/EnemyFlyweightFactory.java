package br.uneb.astrojumper.entities.enemy.factories;

import br.uneb.astrojumper.entities.enemy.EnemyType;
import br.uneb.astrojumper.screens.PlayScreen;

import java.util.HashMap;
import java.util.Map;

public class EnemyFlyweightFactory {
    private static Map<String, EnemyType> enemyTypes = new HashMap<>();

    public static EnemyType getEnemyType(String name, PlayScreen playScreen) {
        EnemyType type = enemyTypes.get(name);
        if (type == null) {
            type = new EnemyType(name, new HashMap<>(), playScreen);
            enemyTypes.put(name, type);
        }
        return type;
    }

    public static void clear() {
        enemyTypes.clear();
    }
}
