package br.uneb.astrojumper.entities.enemy.factories;

import br.uneb.astrojumper.entities.enemy.movable.MovableEnemy;
import br.uneb.astrojumper.entities.enemy.statics.StaticEnemy;
import br.uneb.astrojumper.screens.PlayScreen;

public interface EnemyFactory {
    StaticEnemy createStaticEnemy(PlayScreen playScreen);

    MovableEnemy createMovableEnemy(PlayScreen playScreen);
}
