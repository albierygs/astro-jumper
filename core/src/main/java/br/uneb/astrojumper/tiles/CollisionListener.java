package br.uneb.astrojumper.tiles;

import br.uneb.astrojumper.entities.Astronaut;
import br.uneb.astrojumper.entities.FinalSpaceship;
import br.uneb.astrojumper.entities.Ray;
import br.uneb.astrojumper.entities.SpaceshipBullet;
import br.uneb.astrojumper.entities.enemy.Enemy;
import br.uneb.astrojumper.entities.enemy.movable.MovableEnemyBase;
import br.uneb.astrojumper.entities.meteor.Meteor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object userDataA = fixA.getUserData();
        Object userDataB = fixB.getUserData();

        if (userDataA instanceof SpaceshipBullet) {
            ((SpaceshipBullet) userDataA).colide();
            if (userDataB instanceof Astronaut) {
                ((Astronaut) userDataB).receiveDamage(0, 0); // Player takes damage
            }
        } else if (userDataB instanceof SpaceshipBullet) {
            ((SpaceshipBullet) userDataB).colide();
            if (userDataA instanceof Astronaut) {
                ((Astronaut) userDataA).receiveDamage(0, 0); // Player takes damage
            }
        }

        // colisão do meteoro com o chão para acionar a axplosão
        if (userDataA instanceof Meteor) {
            ((Meteor) userDataA).colide();
        } else if (userDataB instanceof Meteor) {
            ((Meteor) userDataB).colide();
        }


        // lógica para colisão entre astronauta e meteoro
        if ((userDataA instanceof Meteor && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof Meteor)) {

            // astronauta recebendo dano
            if (userDataA instanceof Astronaut) {
                ((Astronaut) userDataA).receiveDamage(0, 0);
            } else {
                ((Astronaut) userDataB).receiveDamage(0, 0);
            }

        } else if ((userDataA instanceof Damage && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof Damage)) {

            // astronauta recebendo dano
            if (userDataA instanceof Astronaut) {
                ((Astronaut) userDataA).receiveDamage(4f, -1);
            } else {
                ((Astronaut) userDataB).receiveDamage(4f, -1);
            }

        } else if ((userDataA instanceof FinalSpaceship && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof FinalSpaceship)) {

            // fim da fase
            if (userDataA instanceof FinalSpaceship) {
                ((FinalSpaceship) userDataA).colide();
            } else {
                ((FinalSpaceship) userDataB).colide();
            }
        } else if ((userDataA instanceof Ray && userDataB instanceof Astronaut) ||
            (userDataA instanceof Astronaut && userDataB instanceof Ray)) {

            // colisão com o raio
            if (userDataA instanceof Ray) {
                ((Ray) userDataA).colide();
            } else {
                ((Ray) userDataB).colide();
            }
        } else if ((userDataA instanceof MovableEnemyBase && userDataB instanceof CollisionTileObject) ||
            (userDataB instanceof MovableEnemyBase && userDataA instanceof CollisionTileObject)) {
            if (userDataA instanceof MovableEnemyBase) {
                ((MovableEnemyBase) userDataA).turn();
            } else {
                ((MovableEnemyBase) userDataB).turn();
            }
        } else if ((userDataA instanceof Astronaut && userDataB instanceof Enemy) ||
            (userDataA instanceof Enemy && userDataB instanceof Astronaut)) {

            Astronaut player = (userDataA instanceof Astronaut) ? (Astronaut) userDataA : (Astronaut) userDataB;
            Enemy enemy = (userDataA instanceof Enemy) ? (Enemy) userDataA : (Enemy) userDataB;

            float playerBottom = player.getBody().getPosition().y - player.getHeight() / 2;
            float enemyTop = enemy.getBody().getPosition().y + enemy.getHeight() / 2;
            float playerSpeedY = player.getBody().getLinearVelocity().y;

            if (playerSpeedY < -0.01f && playerBottom > (enemyTop - 0.2f)) {
                enemy.defeat();

                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
                player.getBody().applyLinearImpulse(new Vector2(0, 5f), player.getBody().getWorldCenter(), true);
            } else {
                if (enemy.getBody() != null) {
                    enemy.getBody().setLinearVelocity(enemy.getBody().getLinearVelocity().x, 0);
                }
                player.receiveDamage(0, 0);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Object userDataA = fixA.getUserData();
        Object userDataB = fixB.getUserData();

        // restaurando estado do astronauta quando a colisão termina
        if (userDataA instanceof Astronaut) {
            ((Astronaut) userDataA).resetDamageState();
        }
        if (userDataB instanceof Astronaut) {
            ((Astronaut) userDataB).resetDamageState();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        if ((userDataA instanceof SpaceshipBullet && ((SpaceshipBullet) userDataA).isFinished()) ||
            (userDataB instanceof SpaceshipBullet && ((SpaceshipBullet) userDataB).isFinished())) {
            contact.setEnabled(false);
        }

        if ((userDataA instanceof MovableEnemyBase && ((MovableEnemyBase) userDataA).isDefeated()) ||
            (userDataB instanceof MovableEnemyBase && ((MovableEnemyBase) userDataB).isDefeated())) {
            contact.setEnabled(false);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
