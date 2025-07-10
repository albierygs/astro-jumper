package br.uneb.astrojumper.entities.enemy.factories;

import br.uneb.astrojumper.entities.enemy.movable.MovableGroundEnemy;
import br.uneb.astrojumper.entities.enemy.statics.StaticGroundEnemy;
import br.uneb.astrojumper.screens.PlayScreen;

public class GroundEnemyFactory implements EnemyFactory {
    @Override
    public StaticGroundEnemy createStaticEnemy(PlayScreen playScreen) {
        return new StaticGroundEnemy(playScreen);
    }

    @Override
    public MovableGroundEnemy createMovableEnemy(PlayScreen playScreen) {
        return new MovableGroundEnemy(playScreen);
    }
}
