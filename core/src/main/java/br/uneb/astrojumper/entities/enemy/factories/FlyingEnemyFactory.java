package br.uneb.astrojumper.entities.enemy.factories;

import br.uneb.astrojumper.entities.enemy.movable.MovableFlyingEnemy;
import br.uneb.astrojumper.entities.enemy.statics.StaticFlyingEnemy;
import br.uneb.astrojumper.screens.PlayScreen;

public class FlyingEnemyFactory implements EnemyFactory {
    @Override
    public StaticFlyingEnemy createStaticEnemy(PlayScreen playScreen) {
        return new StaticFlyingEnemy(playScreen);
    }

    @Override
    public MovableFlyingEnemy createMovableEnemy(PlayScreen playScreen) {
        return new MovableFlyingEnemy(playScreen);
    }
}
